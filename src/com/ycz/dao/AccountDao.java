package com.ycz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.ycz.pojo.Account;

public interface AccountDao {

    void add(Account account);

    void edit(Account account);

    List<Account> findList(Map<String, Object> map);

    int getTotal(Map<String, Object> map);

    @Select("delete from account where id=#{id}")
    void delete(Long id);

    @Select("select * from account where name=#{name}")
    Account queryByName(String name);

}
