package cat.socket;

import cat.dto.User;
import cat.service.BoardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReplyEchoHandler extends TextWebSocketHandler {

    // 전체 접속자의 session 을 담는 리스트
    List<WebSocketSession> sessions = new ArrayList<>();

    // 개별 접속자 session 을 담는 Map
    Map<String, WebSocketSession> userSessions = new HashMap<>();


// 클라이언트가 서버 접속에 성공했을 때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("afterConnectionEstablished="+session);
        sessions.add(session);

        String senderId = getId(session);
        // 로그인되어있으면 (httpsessionId), 안되어있으면 웹소켓 sessionId 을 담음
        userSessions.put(senderId,session);


    }

// 클라이언트가 소켓에 메세지를 전송했을 때
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("handleTextMessage="+session+" : "+message);

        String senderId = getId(session);
        System.out.println("senderId = " + senderId);

//        // 모든유저에게 보냄
//        for(WebSocketSession sess : sessions) {
//            sess.sendMessage(new TextMessage(senderId+" : "+message.getPayload())); // getPayload : 받은 메세지를 그대로 나타내줌
//        }

        // protocol : cmd(기능), 게시물번호 ---- (ex: reply, bno)
        String msg = message.getPayload();
        if(StringUtils.isNotEmpty(msg)) { // message가 null이 아닐때 쪼개기
            String[] strs = msg.split(",");
            if(strs != null && strs.length == 3) {
                String cmd = strs[0];
                String bno = strs[1];
                String boardWriter = strs[2];

                // 새 댓글 알림 전송
                WebSocketSession boardWriterSession = userSessions.get(boardWriter);
                System.out.println("boardWriterSession = " + boardWriterSession);

                /* 게시글 작성자 == 댓글 작성자인 경우에는 알림X */
                if("reply".equals(cmd) && boardWriterSession != null && !senderId.equals(boardWriter)) {
                    TextMessage tmpMsg = new TextMessage("<a href='/board/read?bno=" + bno + "'>" + bno + "</a>번 게시글에 새로운 댓글이 있습니다.");
                    boardWriterSession.sendMessage(tmpMsg);
                }
            }
        }
    }

    private String getId(WebSocketSession session) {
        // HttpSession(Map) 으로부터 id를 얻음
        Map<String, Object> httpSession = session.getAttributes();
        String loginUser = (String)httpSession.get("id");
        System.out.println("loginUser = " + loginUser);

        if(loginUser == null)  // 로그인한 id가 null인 경우 웹소켓 세션id 가져옴
            return session.getId();
        else
            return loginUser;
    }


    // 클라이언트가 서버 접속을 종료 했을 때
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("afterConnectionClosed="+session+" : "+status);
    }


}

