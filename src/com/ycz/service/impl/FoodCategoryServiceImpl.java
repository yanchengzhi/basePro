package com.ycz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.dao.FoodCategoryDao;
import com.ycz.pojo.FoodCategory;
import com.ycz.service.FoodCategoryService;

@Service
public class FoodCategoryServiceImpl implements FoodCategoryService {
    
    @Autowired
    private FoodCategoryDao fDao;

    @Override
    public void add(FoodCategory foodCategory) {
        fDao.add(foodCategory);
    }

    @Override
    public int getTotal(Map<String,Object>map) {
        return fDao.getTotal(map);
    }

    @Override
    public List<FoodCategory> findList(Map<String, Object> map) {
        return fDao.findList(map);
    }

    @Override
    public FoodCategory queryByName(String name) {
        return fDao.queryByName(name);
    }

    @Override
    public void edit(FoodCategory foodCategory) {
        fDao.edit(foodCategory);
    }

    @Override
    public void delete(Long id) {
        fDao.delete(id);
    }

}
