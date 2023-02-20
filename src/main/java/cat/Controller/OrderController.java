package cat.Controller;

import cat.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping("/order")
    public String orderPage(@RequestParam Integer quantity, @RequestParam Integer productId) {
        System.out.println("주문 상품 : "+productId+" 주문 개수 :  " + quantity);


        return "order";
    }
}
