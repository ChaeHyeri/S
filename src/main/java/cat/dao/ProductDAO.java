package cat.dao;

import cat.dto.ProductDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {

    @Autowired
    private SqlSession session;

    private static String namespace="cat.dao.ProductMapper.";

    public List<ProductDTO> selectAll() {
        return session.selectList(namespace+"selectAll");
    }

    public ProductDTO selectOne(Integer id) {
        return session.selectOne(namespace+"selectOne",id);
    }

}
