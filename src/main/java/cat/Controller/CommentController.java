package cat.Controller;

import cat.dto.CommentDTO;
import cat.dto.PageHandler;
import cat.dto.SearchCondition;
import cat.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
//@ResponseBody
@RestController
public class CommentController {
    @Autowired
    CommentService service;

    // 댓글을 수정하는 메서드
    @PatchMapping("/comments/{cno}")   // /comments/1  PATCH
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDTO dto, HttpSession session) {
        String commenter = (String)session.getAttribute("id");
        System.out.println("commenter = " + commenter);
        dto.setCommenter(commenter);
        dto.setCno(cno);
        System.out.println("dto = " + dto);

        try {
            if(service.modify(dto)!=1)
                throw new Exception("Write failed.");

            return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("MOD_ERR", HttpStatus.BAD_REQUEST);
        }
    }


    // 댓글을 등록하는 메서드
    @PostMapping("/comments")   // /ch4/comments?bno=1085  POST

    // @RequestBody : JSON 형식을 자바객체로 변환하여 넣어줌.
    public ResponseEntity<String> write(@RequestBody CommentDTO dto, Integer bno, HttpSession session) {
        String commenter = (String)session.getAttribute("id");
        dto.setCommenter(commenter);
        dto.setBno(bno);
        System.out.println("dto = " + dto);

        try {
            if(service.write(dto)!=1)
                throw new Exception("Write failed.");

            return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // 지정된 댓글을 삭제하는 메서드
    @DeleteMapping("/comments/{cno}")  // DELETE /comments/1?bno=1085  <-- 삭제할 댓글 번호
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session) {
        String commenter = (String)session.getAttribute("id");

        try {
            int rowCnt = service.remove(cno, bno, commenter);

            if(rowCnt!=1)
                throw new Exception("Delete Failed");

            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // 지정된 게시물의 모든 댓글을 가져오는 메서드
    @GetMapping("/comments")  // /comments?bno=1080   GET
    public ResponseEntity<Map<String, Object>> list(Integer bno, Integer page, SearchCondition sc) throws Exception {
        System.out.println("bno = " + bno);
        System.out.println("page = " + page);
        List<CommentDTO> list = null;
        Map<String, Object> map = new HashMap<>();
        try {
            list = service.getList(bno, page);
            System.out.println("list = " + list);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400
        }
        int replyCnt = service.getCount(bno);
        System.out.println("replyCnt = " + replyCnt);
        PageHandler pageHandler = new PageHandler(replyCnt,sc);
        System.out.println("pageHandler = " + pageHandler);

        map.put("list", list);
        map.put("ph", pageHandler);


        return new ResponseEntity<>(map, HttpStatus.OK);  // 200
    }
}