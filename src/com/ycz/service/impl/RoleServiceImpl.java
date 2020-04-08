package com.ycz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.dao.RoleDao;
import com.ycz.pojo.Menu;
import com.ycz.pojo.Role;
import com.ycz.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleDao rDao;

    @Override
    public void add(Role role) {
        rDao.add(role);
    }

    @Override
    public int getTotal(Map<String, Object> map) {
        return rDao.getTotal(map);
    }

    @Override
    public List<Menu> findList(Map<String, Object> map) {
        return rDao.findList(map);
    }

    @Override
    public void edit(Role role) {
        rDao.edit(role);
    }

    @Override
    public void deleteRole(Long id) {
        rDao.deleteRole(id);
    }

    @Override
    public List<Role> getAllRole() {
        return rDao.getAllRole();
    }

}
