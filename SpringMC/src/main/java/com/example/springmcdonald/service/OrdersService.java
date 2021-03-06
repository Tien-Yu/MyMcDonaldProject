/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.service;

import com.example.springmcdonald.pojo.Orders;
import com.example.springmcdonald.pojo.Users;
import com.example.springmcdonald.repository.OrdersRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Orders> findByTrackingNumberAndStatusExcluding(String phone, String status){
        return ordersRepository.findByTrackingNumberAndStatusExcluding(phone, status);
    }
    
    /*查詢用戶或是訪客的Orders時所使用的方法*/
    public List<Orders> findByUsersAndStatusExcluding(Users users, String status){
        return ordersRepository.findByUsersAndStatusExcluding(users, status);
    }
    
    public Page<Orders> findAllByUsers(Users users, int pageNo, int size){
        if(pageNo <= 1){
            pageNo = 1;
        }
        
        Pageable pageable = null;
                
        pageable = PageRequest.of(pageNo-1, size);
        Page<Orders> pageOrders = ordersRepository.findAllByUsers(users, pageable);
        
        int totalPages = pageOrders.getTotalPages();
        if(pageNo >= totalPages){
            pageNo = pageOrders.getTotalPages();
        }
        
        pageable = PageRequest.of(pageNo-1, size);
        
        return ordersRepository.findAllByUsers(users, pageable);
    }
    
    

}
