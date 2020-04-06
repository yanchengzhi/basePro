package com.ycz.service;

import com.ycz.pojo.User;

public interface UserService {

    User queryUser(User user);

    User queryUserByName(String username);

}
