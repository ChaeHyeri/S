package cat.socket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class ChatEchoHandler extends TextWebSocketHandler {

    // 개별 접속자 session 을 담는 Map
    Map<String, WebSocketSession> userSessions = new HashMap<>();


    // 클라이언트가 서버 접속에 성공했을 때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("chat connect");

        String custId = getId(session);
        userSessions.put(custId, session);

    }

    // 클라이언트가 소켓에 메세지를 전송했을 때
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("cust msg");

        String custId = getId(session);
        System.out.println("custId = " + custId);
    }



    private String getId(WebSocketSession session) {
        // HttpSession(Map) 으로부터 id를 얻음
        Map<String, Object> httpSession = session.getAttributes();
        String loginUser = (String) httpSession.get("id");
        System.out.println("loginUser = " + loginUser);

        if (loginUser == null)  // 로그인한 id가 null인 경우 웹소켓 세션id 가져옴
            return session.getId();
        else
            return loginUser;


    }
}
