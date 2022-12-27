package cat.Controller;


import cat.dao.UserDAO;
import cat.dto.User;
import cat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;

    final int FAIL = 0;

    @InitBinder
    public void toDate(WebDataBinder binder) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
        binder.setValidator(new UserValidator()); // UserValidator를 WebDataBinder의 로컬 validator로 등록
        //	List<Validator> validatorList = binder.getValidators();
        //	System.out.println("validatorList="+validatorList);
    }

    @RequestMapping("/register")
    public String register() {
        System.out.println(" register called");

        return "registerpage";
    }

    @PostMapping("/register/save")
    public String save(@Valid User user, BindingResult result, Model m, HttpServletRequest request) throws Exception {
        System.out.println("result="+result);
        System.out.println("user="+user);

        // 1. 에러가 없으면
        if(!result.hasErrors()) {
            // 2. DB에 신규회원 정보를 저장 + 세션에 저장

            int rowCnt = userService.register(user);
            HttpSession session = request.getSession();
            session.setAttribute("id",user.getId());

            if(rowCnt!=FAIL) {
                return "registerInfo";
            }
        }

        return "registerpage";
    }

    private boolean isValid(User user) {
        return true;
    }
}