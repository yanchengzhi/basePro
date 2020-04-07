package com.ycz.service;

import java.util.List;
import java.util.Map;

import com.ycz.pojo.Menu;
import com.ycz.pojo.Role;

public interface RoleService {

    void add(Role role);

    int getTotal(Map<String, Object> map);

    List<Menu> findList(Map<String, Object> map);

    void edit(Role role);

    void deleteRole(Long id);

}
