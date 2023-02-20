package cat.Controller;


import cat.dto.CartDTO;
import cat.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    CartService cartService;



    @GetMapping("/cart")
    public String cart(HttpSession session, Model m) {

        String id = (String)session.getAttribute("id");
        List<CartDTO> list = cartService.cartList(id);
        System.out.println("list = " + list);
        m.addAttribute("cartList",list);

        return "cart";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer productId, @RequestParam Integer quantity, HttpSession session, Model m) throws Exception {
        String id = (String)session.getAttribute("id");
        System.out.println("카트 추가 = " + productId+"번 상품 "+quantity+"개");
        cartService.addCart(id, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/delete")
    public String removeProduct(@RequestParam Integer productId, @RequestParam String loginId) {
        System.out.println("장바구니에서 삭제 : " + productId);
        cartService.deleteFromCart(productId, loginId);

        return "redirect:/cart";
    }
}
