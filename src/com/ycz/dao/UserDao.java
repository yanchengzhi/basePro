package com.ycz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.ycz.pojo.User;

public interface UserDao {

    @Select("select * from user where username=#{username} and password=#{password}")
    User queryUser(User user);

    @Select("select * from user where username=#{username}")
    User queryUserByName(String username);

    void addUser(User user);

    void deleteUser(String ids);

    void editUser(User user);

    List<User> findList(Map<String, Object> map);

    int getTotal(Map<String, Object> map);

    List<User> selectUser(String ids);

    @Select("update user set password=#{password} where id=#{id}")
    void resetPass(User currentUser);

}
