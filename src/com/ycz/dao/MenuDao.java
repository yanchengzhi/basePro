package com.ycz.dao;

import java.util.List;
import java.util.Map;

import com.ycz.pojo.Menu;

public interface MenuDao {

    void add(Menu menu);

    List<Menu> findList(Map<String, Object> map);

    List<Menu> findTopList();

    int getTotal(Map<String, Object> map);

}
