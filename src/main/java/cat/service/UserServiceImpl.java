package cat.service;

import cat.dao.UserDAO;
import cat.dto.User;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserDAO userDao;

    @Override
    public User read(String id) throws Exception {
        return userDao.selectUser(id);
    }
    @Override
    public int register(User user) throws Exception {
        return userDao.insertUser(user);
    }

}
