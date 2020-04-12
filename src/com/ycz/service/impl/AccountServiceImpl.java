package com.ycz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.dao.AccountDao;
import com.ycz.pojo.Account;
import com.ycz.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    
    @Autowired
    private AccountDao acDao;

    @Override
    public void add(Account account) {
      acDao.add(account);  
    }

    @Override
    public void edit(Account account) {
        acDao.edit(account);  
    }

    @Override
    public List<Account> findList(Map<String, Object> map) {
        return acDao.findList(map);
    }

    @Override
    public int getTotal(Map<String, Object> map) {
        return acDao.getTotal(map);
    }

    @Override
    public void delete(Long id) {
        acDao.delete(id);
    }

    @Override
    public Account queryByName(String name) {
        return acDao.queryByName(name);
    }

}
