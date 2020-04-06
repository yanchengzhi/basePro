package com.ycz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.dao.MenuDao;
import com.ycz.pojo.Menu;
import com.ycz.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
    
    @Autowired
    private MenuDao mDao;

    @Override
    public void add(Menu menu) {
        mDao.add(menu);
    }

    @Override
    public List<Menu> findList(Map<String, Object> map) {
        return mDao.findList(map);
    }

    @Override
    public List<Menu> findTopList() {
        return mDao.findTopList();
    }

    @Override
    public int getTotal(Map<String, Object> map) {
        return mDao.getTotal(map);
    }

}
