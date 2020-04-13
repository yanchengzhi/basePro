package com.ycz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.dao.OrderDao;
import com.ycz.pojo.Order;
import com.ycz.pojo.OrderItem;
import com.ycz.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderDao oDao;

    @Override
    public void add(Order order) {
        oDao.add(order);
    }

    @Override
    public void edit(Order order) {
        oDao.edit(order);
    }

    @Override
    public int getTotal(Map<String, Object> map) {
        return oDao.getTotal(map);
    }

    @Override
    public List<Order> findList(Map<String, Object> map) {
        return oDao.findList(map);
    }

    @Override
    public void delete(Long id) {
       oDao.delete(id); 
    }

    @Override
    public void addItem(OrderItem item) {
        oDao.addItem(item);
    }

}
