package com.ycz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.ycz.pojo.Menu;

public interface MenuDao {

    void add(Menu menu);

    List<Menu> findList(Map<String, Object> map);

    List<Menu> findTopList();

    int getTotal(Map<String, Object> map);

    void edit(Menu menu);

    void deleteMenu(Long id);

    List<Menu> findChildren(Long id);

    List<Menu> findMenuList(String ids);

    @Select("select * from menu")
    List<Menu> queryAll();

    @Select("select * from menu where id=#{id}")
    Menu findMenu(Long id);

}
