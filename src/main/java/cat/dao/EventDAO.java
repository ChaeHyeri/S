package cat.dao;

import cat.dto.CommentDTO;
import cat.dto.ReplyEventDto;
import cat.event.NewCommentEvent;

import java.util.List;

public interface EventDAO {

    int saveNewCommentAlarm(String writer, CommentDTO commentDTO) throws Exception;
    List<NewCommentEvent> getEvtList(String id);
}
