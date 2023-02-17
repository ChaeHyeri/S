package cat.Controller;

import cat.dto.CartDTO;
import cat.dto.ProductDTO;
import cat.service.CartService;
import cat.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping(value="/shop")
    public String shop(HttpServletRequest request, Model m) throws Exception {
        if(loginCheck(request))
            return "redirect:/login?toURL="+request.getRequestURL();


        List<ProductDTO> list = productService.getList();

        m.addAttribute("productList",list);
        return "shop";
    }

    @GetMapping(value="/productDetail")
    public String productDetail(@RequestParam Integer id, Model m) {

        /* id 값으로 상세 정보 조회 */
        ProductDTO product = productService.getProductById(id);
        m.addAttribute("product", product);

        return "productDetail";
    }




    private boolean loginCheck(HttpServletRequest request) {
        System.out.println(" login check ");
        HttpSession session = request.getSession();
        return session.getAttribute("id")==null;
    }
}
