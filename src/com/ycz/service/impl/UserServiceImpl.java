package com.ycz.service.impl;

import java.util.List;
import java.util.Map;

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

    @Override
    public void addUser(User user) {
        uDao.addUser(user);
    }

    @Override
    public void deleteUser(String ids) {
        uDao.deleteUser(ids);
    }

    @Override
    public void editUser(User user) {
        uDao.editUser(user);
    }

    @Override
    public List<User> findList(Map<String, Object> map) {
        return uDao.findList(map);
    }

    @Override
    public int getTotal(Map<String, Object> map) {
        return uDao.getTotal(map);
    }

}
