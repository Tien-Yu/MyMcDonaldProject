/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.controller;

import com.example.springmcdonald.pojo.OrderLine;
import com.example.springmcdonald.pojo.Orders;
import com.example.springmcdonald.pojo.Users;
import com.example.springmcdonald.service.OrdersService;
import com.example.springmcdonald.service.UsersService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nathan
 */
@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    OrdersService ordersService;
    @Autowired
    UsersService usersService;

    /**
     *
     * @param session
     * @return
     */
    @GetMapping("/status")
    public String show_orders(HttpSession session) {
        if (true) {
            //empty orders 
        }

        return "OrdersHome";
    }

    /**
     * Calendar 使用時須用方法轉換數值
     *
     * @param session
     * @return
     */
    @GetMapping("/submit")
    public String submit(HttpSession session) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<OrderLine> orderLines = (List) session.getAttribute("orderLines");

        /* create order date*/
        Locale locale = Locale.getDefault();
        Calendar calenedar = Calendar.getInstance(locale);

        /*insert into orders fields*/
        Orders orders = new Orders();
        orders.setOrderLines(orderLines);
        orders.setOrderdate(calenedar);

        /* conditional - login user / non user */
        if (session.getAttribute("name") != null) {
            Users users = usersService.findByName((String) session.getAttribute("name")).get();
            orders.setUsers(users);
            if (session.getAttribute("phone") == null) {
                System.out.println("phone is null, must get it form users object");
            }
        }

        if (session.getAttribute("phone") != null) {
            orders.setTrackingNumber((String) session.getAttribute("phone"));
        }

        ordersService.save(orders);

//        /* 分開用戶與訪客 -> 查詢狀態為非完成 */
//        List<Orders> ordersList = (List)session.getAttribute("orders");
//        if(ordersList == null){
//            ordersList = new ArrayList<>();            
//            session.setAttribute("ordersList", ordersList);
//        }
//        ordersList.add(orders);
        /* 判斷訂單有無對應到客戶*/
//        redirect:/orders/status
        return "OrdersHome";
    }

}
