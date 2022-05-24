/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.controller;

import com.example.springmcdonald.pojo.OrderLine;
import com.example.springmcdonald.pojo.Orders;
import com.example.springmcdonald.pojo.Users;
import com.example.springmcdonald.service.OrderLineService;
import com.example.springmcdonald.service.OrdersService;
import com.example.springmcdonald.service.UsersService;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    OrderLineService orderLineService;

    /**
     *
     * @param session
     * @param m
     * @return
     */
    @GetMapping("/status")
    public String show_orders(HttpSession session, Model m) {
        /*users*/
        if (session.getAttribute("name") != null) {
            Users users = usersService.findByName((String) session.getAttribute("name")).get();
            List<Orders> orderList
                    = ordersService.findByUsersAndStatusExcluding(users, "delivered");
            session.setAttribute("orderList", orderList);
            return "PurchasedOrders";
        }

        /*non-users*/
        if (session.getAttribute("phone") != null) {
            String phone = (String) session.getAttribute("phone");
            List<Orders> orderList
                    = ordersService.findByTrackingNumberAndStatusExcluding(phone, "delivered");
            if (orderList == null) {

                return "FindOrders"; //查無訂單資料 (上一頁) - 未建立  重要 > 大部分都會跑到這裡

            }

            session.setAttribute("orderList", orderList);
            session.removeAttribute("phone");
            return "PurchasedOrders";
        }

        return "FindOrders";
    }

    @GetMapping("/history/master/{pageNo}")
    public String users_orders(@PathVariable(value = "pageNo") int pageNo,
                               @RequestParam(defaultValue = "5") int size, HttpSession session, Model m) {
        /*users*/
        if (session.getAttribute("name") != null) {
            Users users = usersService.findByName((String) session.getAttribute("name")).get();
            Page<Orders> pageOrders
                    = ordersService.findAllByUsers(users, pageNo, size);

            List<Orders> orderList = pageOrders.getContent();
            
            //if there is only one page, pageNo value will always be 1 (Thymeleaf use)
            int totalPages = pageOrders.getTotalPages();            
            if(totalPages == 1){
                pageNo = 1;
            }
            if(pageNo < 1){
                pageNo = 1;
            }else if(pageNo > totalPages){
                pageNo = totalPages;
                orderList = null;
            }

            //當前頁次
            m.addAttribute("currentPage", pageNo);
            //每頁筆數
            m.addAttribute("pageSize", size);
            //總頁次
            m.addAttribute("totalPages", totalPages);
            //總筆數
            m.addAttribute("totalItems", pageOrders.getTotalElements());

            session.setAttribute("orderList", orderList);
            return "PurchasedOrders";
        }
        return "redirect:/menu";
    }
    
    @GetMapping("/history/master")
    public String orders_history(HttpSession session, Model model){
        return users_orders(1, 5, session, model);        
    }

    /**
     * Calendar 使用時須用方法轉換數值 > https://www.baeldung.com/dates-in-thymeleaf
     *
     * @param onclick
     * @param session
     * @return
     */
    @GetMapping("/submit")
    public String submit(@RequestParam(defaultValue = "") String onclick ,HttpSession session) {
        /*get list(orderLines) - for the use of condition*/
        List<OrderLine> orderLines = (List) session.getAttribute("orderLines");        
        if (orderLines == null) {
            return "redirect:/menu/shoppingcart";
        }

        Orders orders = new Orders(); //此Orders需要先在資料庫建立出來才能用來插入到orderLines中
        ordersService.save(orders);

        /*update orderLines information*/
        orderLines.forEach(ol -> {
            ol.setOrders(orders);
            orderLineService.insert(ol);
        });


        /* create orderdate*/
        Locale locale = Locale.getDefault();
        Calendar calenedar = Calendar.getInstance(locale);

        /*insert value to each orders fields*/
        orders.setOrderLines(orderLines);
        orders.setOrderdate(calenedar);

        session.removeAttribute("orderLines");

        /* conditional - login user / non user */
        if (session.getAttribute("name") != null) {
            Users users = usersService.findByName((String) session.getAttribute("name")).get();
            orders.setUsers(users);
            if (session.getAttribute("phone") == null) {
                System.out.println("phone is null, it can be get form users object(not implemented)");
                orders.setTrackingNumber("0911031071"); //Hard code                
            }
        }
        if (session.getAttribute("phone") != null) {
            orders.setTrackingNumber((String) session.getAttribute("phone"));
        }

        ordersService.save(orders);
        
        if(onclick.equals("homepage")){            
            return "redirect:/menu";
        }
        
        return "redirect:/menu/shoppingcart";
    }

    /**
     *
     * @param phone
     * @param session
     * @param redirectAttributes
     * @return
     */
    @PostMapping("phonePosting")
    public String phonePosting(String phone, HttpSession session, RedirectAttributes redirectAttributes) {
        if (ordersService.findByTrackingNumberAndStatusExcluding(phone, "delivered").isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "查無" + phone + "的訂單資訊");
            return "redirect:/orders/status";
        }

        session.setAttribute("phone", phone);

        return "redirect:/orders/status";
    }

}
