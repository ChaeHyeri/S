package cat.dao;

import cat.dto.User;

public interface UserDAO {
    User selectUser(String id) throws Exception;
    int insertUser(User user) throws Exception;
    int updateUser(User user) throws Exception;


}
