package cat.Controller;

import cat.dao.UserDAO;
import cat.dto.User;
import cat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
/* loginId 가 세션에 저장됨 */
@SessionAttributes({"loginId"})
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginpage() {
        return "loginpage";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";

    }

    @PostMapping("/login")
    public String login(String id, String pwd, String toURL, boolean rememberId, Model m,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 유효성 검사
            // 일치하지 않을 경우
            if (!loginCheck(id, pwd)) {
                String msg = URLEncoder.encode("id 또는 비밀번호가 일치하지 않습니다.", "utf-8");
                m.addAttribute("msg",msg);

                return "redirect:/login?msg="+msg;
            }

            // 일치할 경우 세션에 저장
            HttpSession session = request.getSession();
            session.setAttribute("id",id);


            // 아이디 기억 체크한 경우 쿠키에 저장
            if(rememberId){
                Cookie cookie = new Cookie("id",id);
                response.addCookie(cookie);
            } else {  // 체크하지 않은 경우 쿠키 삭제
                Cookie cookie = new Cookie("id", id);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }

        toURL = toURL==null || toURL.equals("") ? "/" : toURL;
        return "redirect:"+toURL;



    }



    private boolean loginCheck(String id, String pwd) throws Exception {


        User user = userService.read(id);
        System.out.println("user = " + user);

        if(user==null) return false;

        return user.getPwd().equals(pwd);
    }
}


