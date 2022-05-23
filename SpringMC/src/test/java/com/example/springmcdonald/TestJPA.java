/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald;

import com.example.springmcdonald.pojo.Orders;
import com.example.springmcdonald.pojo.Product;
import com.example.springmcdonald.pojo.Users;
import com.example.springmcdonald.service.OrdersService;
import com.example.springmcdonald.service.ProductService;
import com.example.springmcdonald.service.UsersService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Nathan
 */

@SpringBootTest
public class TestJPA {
    @Autowired
    OrdersService ordersService;
    @Autowired
    UsersService usersService;
    @Autowired
    ProductService productService;
    
    @Test
    void test(){
      
        Page<Product> products = productService.findAllByCategoryNot("SHARE", 1, 5);
        products.stream().map(p -> p.getName()).forEach(System.out::println);
        

    }
}
