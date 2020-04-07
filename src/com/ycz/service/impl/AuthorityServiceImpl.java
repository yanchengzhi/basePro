package com.ycz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.dao.AuthorityDao;
import com.ycz.pojo.Authority;
import com.ycz.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    
    @Autowired
    private AuthorityDao aDao;

    @Override
    public void add(Authority auth) {
        aDao.add(auth);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        aDao.deleteByRoleId(roleId);
    }

    @Override
    public List<Authority> findListByRoleId(Long roleId) {
        return aDao.findListByRoleId(roleId);
    }

}
