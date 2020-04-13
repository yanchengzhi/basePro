package com.ycz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.dao.FoodDao;
import com.ycz.pojo.Food;
import com.ycz.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {
    
    @Autowired
    private FoodDao foodDao;

    @Override
    public void add(Food food) {
        foodDao.add(food);
    }

    @Override
    public void edit(Food food) {
        foodDao.edit(food);
    }

    @Override
    public void delete(Long id) {
        foodDao.delete(id);
    }

    @Override
    public int getTotal(Map<String, Object> map) {
        return foodDao.getTotal(map);
    }

    @Override
    public List<Food> findList(Map<String, Object> map) {
        return foodDao.findList(map);
    }

    @Override
    public Food queryByName(String name) {
        return foodDao.queryByName(name);
    }

    @Override
    public Food queryById(Long id) {
        return foodDao.queryById(id);
    }

    @Override
    public void updateSales(Long id,Long num) {
        foodDao.updateSales(id,num);    
    }
    

}
