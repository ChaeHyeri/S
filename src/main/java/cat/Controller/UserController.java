package cat.Controller;

import cat.dto.User;
import cat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/me")
    public String myPage(HttpServletRequest request, Model m) throws Exception {
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("id");
        m.addAttribute("userid",userId);

        User user = userService.read(userId);
        m.addAttribute("userInfo",user);

        return "myPage";
    }

    @PostMapping("/me/update")
    public String update(User user) {
        System.out.println("user = " + user);

        return "myPage";
    }


}
