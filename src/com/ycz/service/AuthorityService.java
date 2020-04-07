package com.ycz.service;

import java.util.List;

import com.ycz.pojo.Authority;

public interface AuthorityService {
    
    void add(Authority auth);
    
    void deleteByRoleId(Long roleId);
    
    List<Authority> findListByRoleId(Long roleId);

}
