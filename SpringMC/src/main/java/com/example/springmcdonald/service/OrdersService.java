/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.service;

import com.example.springmcdonald.pojo.Orders;
import com.example.springmcdonald.repository.OrdersRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nathan
 */
@Service
public class OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    public List<Orders> findAll(){
        return ordersRepository.findAll();
    }
    
    public Optional<Orders> findById(int id) {
        return ordersRepository.findById(id);
    }
    
    /* 新增Orders + 改訂單狀態時使用(update)*/
    public void save(Orders orders) {
        ordersRepository.save(orders);       
    }
    
    /*刪除Orders 基本上只有測試時使用*/
    public void deleteById(int id) {
        ordersRepository.deleteById(id);
    }
    
    /*查詢狀態為處理或未處理時所用之方法 */
    public List<Orders> findByStatus(String status){
        return ordersRepository.findByStatus(status);
    }
    
    

}
