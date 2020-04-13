package com.ycz.service;

import java.util.List;
import java.util.Map;

import com.ycz.pojo.Order;
import com.ycz.pojo.OrderItem;

public interface OrderService {
    
    void add(Order order);
    
    void edit(Order order);
    
    int getTotal(Map<String,Object>map);
    
    List<Order> findList(Map<String,Object>map);
    
    void delete(Long id);
    
    void addItem(OrderItem item);//添加订单的子项

}
