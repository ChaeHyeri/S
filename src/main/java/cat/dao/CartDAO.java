package cat.dao;

import cat.dto.CartDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartDAO {

    @Autowired
    private SqlSession session;
    private static String namespace="cat.dao.CartMapper.";


    public int insert(CartDTO cartDTO) {
        System.out.println("cartDTO ======== " + cartDTO);
        return session.insert(namespace+"insert",cartDTO);
    }

    public CartDTO checkProduct(CartDTO cartDTO) {
        return session.selectOne(namespace + "select",cartDTO);
    }

//    public List<CartDTO> myCart(String id) {
//        return session.selectList(namespace+"myCart", id);
//    }
    public List<CartDTO> myCart(String userId) {
        return session.selectList(namespace + "myCart", userId);
}

    public int updateCartQuantity(String id, Integer productId, Integer newQuantity) {
        System.out.println("newQuantity = " + newQuantity);

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", id);
        map.put("product_id", productId);
        map.put("quantity", newQuantity);
        return session.update(namespace + "updateCartQuantity", map);
    }
}
