package com.ycz.dao;

import org.apache.ibatis.annotations.Select;

import com.ycz.pojo.User;

public interface UserDao {

    @Select("select * from user where username=#{username} and password=#{password}")
    User queryUser(User user);

    @Select("select * from user where username=#{username}")
    User queryUserByName(String username);

}
