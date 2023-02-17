package cat.service;

import cat.dao.ProductDAO;
import cat.dto.BoardDTO;
import cat.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDAO productDAO;

    public List<ProductDTO> getList() throws Exception {
        return productDAO.selectAll();
    }

    public ProductDTO getProductById(Integer id) {
        return productDAO.selectOne(id);
    }
}
