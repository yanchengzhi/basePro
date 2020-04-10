package com.ycz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.ycz.pojo.Menu;
import com.ycz.pojo.Role;

public interface RoleDao {

    void add(Role role);

    int getTotal(Map<String, Object> map);

    List<Menu> findList(Map<String, Object> map);

    void edit(Role role);

    @Select("delete from role where id=#{id}")
    void deleteRole(Long id);

    @Select("select * from role")
    List<Role> getAllRole();

    @Select("select * from role where id=#{roleId}")
    Role findRoleByRoleId(Long roleId);

}
