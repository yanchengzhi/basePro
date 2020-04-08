package com.ycz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.ycz.pojo.Authority;

public interface AuthorityDao {

    void add(Authority auth);

    @Select("delete from authority where role_id=#{roleId}")
    void deleteByRoleId(Long roleId);

    List<Authority> findListByRoleId(Long roleId);

}
