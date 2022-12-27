package cat.dao;


import cat.dto.BoardDTO;
import cat.dto.CommentDTO;
import cat.dto.ReplyEventDto;
import cat.event.NewCommentEvent;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EventDAOImpl  implements EventDAO {

    @Autowired
    private SqlSession session;
    private static String namespace="cat.dao.EventMapper.";

    @Override
    public int saveNewCommentAlarm(String writer, CommentDTO commentDTO) {
        Map map = new HashMap();
        map.put("writer", writer);
        map.put("commenter", commentDTO.getCommenter());
        map.put("bno", commentDTO.getBno());
        map.put("comment", commentDTO.getComment());
        System.out.println("map = " + map);
        return session.insert(namespace+"insert",map);
    }

    @Override
    public List<NewCommentEvent> getEvtList(String id) {
        return session.selectList(namespace+"getEvtList", id);

    }
}
