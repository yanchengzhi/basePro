package com.ycz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.ycz.pojo.Order;

public interface OrderDao {

    void add(Order order);

    void edit(Order order);

    int getTotal(Map<String, Object> map);

    List<Order> findList(Map<String, Object> map);

    @Select("delete * from order where id=#{id}")
    void delete(Long id);

}
