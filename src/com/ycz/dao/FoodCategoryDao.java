package com.ycz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.ycz.pojo.FoodCategory;

public interface FoodCategoryDao {

    @Select("insert into food_category(name,remark) values(#{name},#{remark})")
    void add(FoodCategory foodCategory);

    int getTotal(Map<String,Object>map);

    List<FoodCategory> findList(Map<String, Object> map);

    @Select("select * from food_category where name=#{name}")
    FoodCategory queryByName(String name);

    @Select("update food_category set name=#{name},remark=#{remark} where id=#{id}")
    void edit(FoodCategory foodCategory);

    @Select("delete from food_category where id=#{id}")
    void delete(Long id);

}
