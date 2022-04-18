/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald;

import com.example.springmcdonald.pojo.Orders;
import com.example.springmcdonald.service.OrdersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Nathan
 */

@SpringBootTest
public class TestOrder {
    @Autowired
    OrdersService ordersService;
    
    @Test
    void testForDefaultValue(){
        Orders orders = new Orders();
        ordersService.save(orders);
        
        System.out.println(orders.getStatus());
        
        
    }
}
