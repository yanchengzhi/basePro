package com.ycz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.dao.UserDao;
import com.ycz.pojo.User;
import com.ycz.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserDao uDao;

    @Override
    public User queryUser(User user) {
        return uDao.queryUser(user);
    }

    @Override
    public User queryUserByName(String username) {
        return uDao.queryUserByName(username);
    }

}
