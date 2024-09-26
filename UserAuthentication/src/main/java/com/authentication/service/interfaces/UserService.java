package com.authentication.service.interfaces;

import com.authentication.dto.LoginCredentials;
import com.authentication.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

//user service layer...
@Service
public interface UserService {
    //methods...
    //1. create user/register...
    public User register(User u);

    //2. login user/return token...
    public String login(LoginCredentials lc);

    //3. update user...
    public User update(String fullToken, User u);

    //4. delete user...
    public String delete(String fullToken);

    //5. find self account...
    public User findSelf(String fullToken);

    //6. find all accounts...
    //only admin roled user can access...
    public List<User> findAll(String fullToken);
}
