package cat.service;

import cat.dao.CartDAO;
import cat.dao.ProductDAO;
import cat.dao.UserDAO;
import cat.dto.CartDTO;
import cat.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    @Autowired
    CartDAO cartDAO;

    @Autowired
    ProductDAO productDAO;

    public List<CartDTO> cartList(String id) {
        return cartDAO.myCart(id);
    }

    @Transactional
    public int addCart(String id, Integer productId, Integer quantity) throws Exception {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setUser_id(id);
        cartDTO.setProduct_id(productId);
        cartDTO.setQuantity(quantity);

        /* 같은 상품번호가 있는지 먼저 확인 */
        CartDTO checkProduct = cartDAO.checkProduct(cartDTO);
        /* 같은 상품번호가 존재할 경우 본래 수량 + 추가한 수량만큼 update */
        if(checkProduct != null) {
            int updateQuantity = checkProduct.getQuantity() + quantity;

            /* 상품 재고 확인 */
            ProductDTO product = productDAO.selectOne(cartDTO.getProduct_id());

            if(product.getStock() < updateQuantity) {
                System.out.println(" 재고부족 ");
                return 0;
            }
            return cartDAO.updateCartQuantity(cartDTO.getUser_id(), cartDTO.getProduct_id(), updateQuantity); }
        else {
            return cartDAO.insert(cartDTO);
        }
    }

    public int deleteFromCart(Integer productId, String id) {
        return cartDAO.removeProduct(productId, id);
    }
}
