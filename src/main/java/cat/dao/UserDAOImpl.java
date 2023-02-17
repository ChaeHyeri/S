package cat.dao;


import cat.dto.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

//@Component - @Controller, @Repository, @Service, @ControllerAdvice
@Repository
@Primary
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SqlSession session;
    private static String namespace="cat.dao.UserMapper.";

    @Override
    public User selectUser(String id) throws Exception {
        return session.selectOne(namespace + "select", id);
    }

    @Override
    public int insertUser(User user) throws Exception {
        return session.insert(namespace+"insert", user);
    }

    @Override
    public int updateUser(User user) throws Exception {
        return session.update(namespace+"update",user);
    }
}