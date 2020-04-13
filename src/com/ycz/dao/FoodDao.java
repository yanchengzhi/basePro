package com.ycz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.ycz.pojo.Food;

public interface FoodDao {

    void add(Food food);

    void edit(Food food);

    @Select("delete from food where id=#{id}")
    void delete(Long id);

    int getTotal(Map<String, Object> map);

    List<Food> findList(Map<String, Object> map);

    @Select("select * from food where name=#{name}")
    Food queryByName(String name);

    @Select("select * from food where id=#{id}")
    Food queryById(Long id);

    void updateSales(Long id,Long num);

}
