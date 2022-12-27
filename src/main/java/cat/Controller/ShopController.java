package cat.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopController {

    @RequestMapping(value="/shop")
    public String shop(){
        return "shop";
    }
}
