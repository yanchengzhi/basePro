package com.ycz.service;

import java.util.List;
import java.util.Map;

import com.ycz.pojo.Food;

public interface FoodService {
    
    void add(Food food);
    
    void edit(Food food);
    
    void delete(Long id);
    
    int getTotal(Map<String,Object> map);
    
    List<Food> findList(Map<String,Object> map);

    Food queryByName(String name);
    
    Food queryById(Long id);
    
    void updateSales(Long id,Long num);//更新销量

}
