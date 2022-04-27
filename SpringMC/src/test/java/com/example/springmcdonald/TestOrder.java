/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald;

import com.example.springmcdonald.pojo.Orders;
import com.example.springmcdonald.pojo.Users;
import com.example.springmcdonald.service.OrdersService;
import com.example.springmcdonald.service.UsersService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

/**
 *
 * @author Nathan
 */

@SpringBootTest
public class TestOrder {
    @Autowired
    OrdersService ordersService;
    @Autowired
    UsersService usersService;
    
    @Test
    void test(){
       Users users = usersService.findByName("timothy").get();        
       Page<Orders> orders = ordersService.findAllByUsers(users, 1, 1);
        System.out.println(orders.getTotalPages());
        System.out.println(orders.getTotalElements());
        
        
    }
}
