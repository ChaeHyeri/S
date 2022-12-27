package cat.service;

import cat.dto.CommentDTO;
import cat.event.NewCommentEvent;

import java.util.List;

public interface EventService {
    List<NewCommentEvent> getEvtList(String writer) throws Exception;
}
