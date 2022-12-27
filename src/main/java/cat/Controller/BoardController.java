package cat.Controller;

import cat.dto.BoardDTO;
import cat.dto.PageHandler;
import cat.dto.SearchCondition;
import cat.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {
    @Autowired
    BoardService boardService;

    @PostMapping("board/modify")
    public String modify(BoardDTO boardDto, Integer page, SearchCondition sc, RedirectAttributes rattr, Model m, HttpSession session) {
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            // 수정 실패 시
            if (boardService.modify(boardDto)!= 1)
                throw new Exception("Modify failed.");

            // 수정 성공 시
            m.addAttribute("searchCondition",sc);
            rattr.addFlashAttribute("msg", "MOD_OK");
            return "redirect:/board"+sc.getQueryString(page);

        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("boardDto",boardDto);
            m.addAttribute("msg", "MOD_ERR");
            return "board";
        }
    }

    @GetMapping("board/write")
    public String write(Integer page, Integer pageSize, Model m) {
        m.addAttribute("mode", "new");
        m.addAttribute("page",page);
        m.addAttribute("pageSize",pageSize);

        return "board";
    }

    @PostMapping("board/write")
    public String write(BoardDTO boardDto, SearchCondition sc,RedirectAttributes rattr, Model m, HttpSession session) {
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            if (boardService.write(boardDto) != 1)
                throw new Exception("Write failed.");

            rattr.addFlashAttribute("msg", "WRT_OK");
            System.out.println(" boardDto="+boardDto);
            return "redirect:/board";

        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);
            m.addAttribute("mode", "new");
            m.addAttribute("msg", "WRT_ERR");
            return "board";
        }
    }

    @PostMapping("board/remove")
    public String remove(Integer bno, Integer page, SearchCondition sc, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr, HttpServletRequest request) {
        String writer = (String)session.getAttribute("id");
        String msg = "DEL_OK";
        try {
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);

            int rowCnt = boardService.remove(bno, writer);
            if(rowCnt!=1)
                throw new Exception("board remove error");

            rattr.addFlashAttribute("msg","DEL_OK");
            // addFlashAttribute : 메세지가 1번만 나옴

        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg","DEL_ERR");
        }

        return "redirect:/board"+sc.getQueryString(page);
    }


    @GetMapping("board/read")
    public String read(@RequestParam("bno") Integer bno, Integer page, Integer pageSize, Model m) {
        try {
            BoardDTO boardDto = boardService.read(bno);
            m.addAttribute("boardDto",boardDto); // 모델을 통해 board.jsp로 전달
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);
            int view = boardService.viewCount(bno);
            System.out.println("view = " + view);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "board";

    }

    @RequestMapping("/board")
    // ==  list(@ModelAttribute SearchCondition sc)
    public String list(SearchCondition sc, Integer page, Integer pageSize, Model m, HttpServletRequest request) {

        if(loginCheck(request))
            return "redirect:/loginpage?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동
        //
        if(page==null) page=1;
        if(pageSize==null) pageSize=10;

        try {

            int totalCnt = boardService.getSearchResultCnt(sc);
            System.out.println("totalCnt = " + totalCnt);
            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            List<BoardDTO> list = boardService.getSearchResultPage(sc);
            m.addAttribute("list",list);
            m.addAttribute("ph", pageHandler);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
            m.addAttribute("totalCnt", totalCnt);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            System.out.println("startOfToday = " + startOfToday);
            m.addAttribute("startOfToday", startOfToday.toEpochMilli());




        } catch (Exception e) {
            e.printStackTrace();
        }


        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("id");
        System.out.println("userId = " + userId);
        m.addAttribute("userid",userId);
        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    private boolean loginCheck(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        return session == null;
    }
}