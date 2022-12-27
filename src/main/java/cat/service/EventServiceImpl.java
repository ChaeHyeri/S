package cat.service;

import cat.dao.BoardDAO;
import cat.dao.EventDAO;
import cat.dto.CommentDTO;
import cat.event.NewCommentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventDAO eventDAO;

    @Autowired
    BoardDAO boardDAO;

    @Override
    public List<NewCommentEvent> getEvtList(String writer) throws Exception {
        return eventDAO.getEvtList(writer);
    }
}
