package com.ycz.service;

import java.util.List;
import java.util.Map;

import com.ycz.pojo.Account;

public interface AccountService {
    
    void add(Account account);
    
    void edit(Account account);
    
    List<Account> findList(Map<String,Object>map);
    
    int getTotal(Map<String,Object>map);
    
    void delete(Long id);
    
    Account queryByName(String name);

}
