package com.ycz.service;

import java.util.List;
import java.util.Map;

import com.ycz.pojo.User;

public interface UserService {

    User queryUser(User user);

    User queryUserByName(String username);
    
    void addUser(User user);
    
    void deleteUser(String ids);//批量删除用户
    
    void editUser(User user);
    
    List<User> findList(Map<String,Object> map);
    
    int getTotal(Map<String,Object> map);

}
