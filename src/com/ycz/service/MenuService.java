package com.ycz.service;

import java.util.List;
import java.util.Map;

import com.ycz.pojo.Menu;

public interface MenuService {

    void add(Menu menu);

    List<Menu> findList(Map<String, Object> map);
    
    List<Menu> findTopList();
    
    int getTotal(Map<String, Object> map);

    void edit(Menu menu);

    void deleteMenu(Long id);

    List<Menu> findChildren(Long id);

}
