package cat.service;

import cat.dto.User;

public interface UserService {

    User read(String id) throws Exception;
    int register(User user) throws Exception;
}
