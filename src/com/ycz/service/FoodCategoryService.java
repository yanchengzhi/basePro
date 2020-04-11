package com.ycz.service;

import java.util.List;
import java.util.Map;

import com.ycz.pojo.FoodCategory;

public interface FoodCategoryService {
    
    void add(FoodCategory foodCategory);
    
    int getTotal(Map<String,Object>map);
    
    List<FoodCategory> findList(Map<String,Object>map);
    
    FoodCategory queryByName(String name);

    void edit(FoodCategory foodCategory);

    void delete(Long id);

}
