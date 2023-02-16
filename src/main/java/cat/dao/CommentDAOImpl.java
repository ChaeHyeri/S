package cat.dao;

import cat.dto.CommentDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDAOImpl implements CommentDAO {
    @Autowired
    private SqlSession session;
    private static String namespace ="cat.dao.CommentMapper.";

    @Override
    public int count(Integer bno) throws Exception {
        return session.selectOne(namespace+"count", bno);
    } // T selectOne(String statement)

    @Override
    public int deleteAll(Integer bno) {
        return session.delete(namespace+"deleteAll", bno);
    } // int delete(String statement)

    @Override
    public int delete(Integer cno, String commenter) throws Exception {
        Map map = new HashMap();
        map.put("cno", cno);
        map.put("commenter", commenter);
        return session.delete(namespace+"delete", map);
    } // int delete(String statement, Object parameter)

    @Override
    public int insert(CommentDTO dto) throws Exception {
        System.out.println(" comment insert");
        return session.insert(namespace+"insert", dto);
    } // int insert(String statement, Object parameter)

    @Override
    public List<CommentDTO> selectAll(Integer bno, int offset) throws Exception {
        Map<String, Object> map = new HashMap<>();
        System.out.println("offset = " + offset);
        map.put("bno", bno);
        map.put("offset", offset);
        map.put("limit", 10);
        return session.selectList(namespace+"selectAll", map);
    } // List<E> selectList(String statement)

    @Override
    public CommentDTO select(Integer cno) throws Exception {
        return session.selectOne(namespace + "select", cno);
    } // T selectOne(String statement, Object parameter)

    @Override
    public int update(CommentDTO dto) throws Exception {
        return session.update(namespace+"update", dto);
    } // int update(String statement, Object parameter)
}