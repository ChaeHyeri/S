package cat.Controller;

import cat.dto.CommentDTO;
import cat.event.NewCommentEvent;
import cat.service.CommentService;
import cat.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/events")
    public ResponseEntity<List<NewCommentEvent>> alarmList(HttpSession session) {
        List<NewCommentEvent> newCmtList = null;
        try {
            String writer = (String)session.getAttribute("id");
            newCmtList = eventService.getEvtList(writer);
            System.out.println("newCmtList = " + newCmtList);
            return new ResponseEntity<List<NewCommentEvent>>(newCmtList, HttpStatus.OK);  // 200
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<NewCommentEvent>>(HttpStatus.BAD_REQUEST); // 400
        }

    }
}
