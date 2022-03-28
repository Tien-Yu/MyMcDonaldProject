/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.webtools;

import com.example.springmcdonald.pojo.OrderLine;
import com.example.springmcdonald.pojo.Product;
import com.example.springmcdonald.service.OrderLineService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nathan
 */
public class OrderLineTools {

    public static void orderLineDivider(Queue<String> queue, int price, int count, Product product, 
                                                                                   OrderLineService orderLineService,
                                                                                   HttpSession session) {
        OrderLine orderLine = new OrderLine();
        orderLine.setProduct(product);
        orderLine.setAmount(count);
        orderLine.setPurchasePrice(price);
        orderLineService.insert(orderLine); //insert to database
        sessionSave(session, orderLine); //save information to session
    }
    
    public static void orderLineDivider_course(Queue<String> queue, int price, int countsp, Product product, 
                                                                                            OrderLineService orderLineService,
                                                                                            HttpSession session) {
        for (int i = 1; i <= countsp; i++) {
            OrderLine orderLine = new OrderLine();
                orderLine.setProduct(product);
                orderLine.setAmount(1);
                orderLine.setPurchasePrice(price + 68);
                String[] tmpSelection = new String[2];
                for (int j = 1; j <= 2; j++) {
                    tmpSelection[j - 1] = queue.poll();
                }
                orderLine.setSelection(tmpSelection);
                orderLineService.insert(orderLine); //insert to database
                sessionSave(session, orderLine); //save information to session        
        }
    }
    
    public static void orderLineDivider_share(Queue<String> queue, int price, int count, Product product, 
                                                                                         OrderLineService orderLineService,
                                                                                         HttpSession session) {
        if (product.getId() == 16) {
            for (int i = 1; i <= count; i++) {
                OrderLine orderLine = new OrderLine();
                orderLine.setProduct(product);
                orderLine.setAmount(1);
                orderLine.setPurchasePrice(price);
                String[] selection = new String[4];
                    for (int j = 1; j <= 4; j++) {
                        selection[j - 1] = queue.poll();
                    }
                    orderLine.setSelection(selection);               
                    orderLineService.insert(orderLine); //insert to database
                    sessionSave(session, orderLine); //save information to session
            }
        }else {
            for (int i = 1; i <= count; i++) {
                OrderLine orderLine = new OrderLine();
                orderLine.setProduct(product);
                orderLine.setAmount(1);
                orderLine.setPurchasePrice(price);
                String[] selection = new String[2];
                for (int j = 1; j <= 2; j++) {
                    selection[j - 1] = queue.poll();
                }
                orderLine.setSelection(selection);
                orderLineService.insert(orderLine); //insert to database
                sessionSave(session, orderLine); //save information to session
            }
        }
    }

    private static void sessionSave(HttpSession session, OrderLine orderLine) {
        List<OrderLine> orderLines = (List) session.getAttribute("orderLines");
        if (orderLines == null) {
            orderLines = new ArrayList();
            session.setAttribute("orderLines", orderLines);
        }
        orderLines.add(orderLine);
        session.setAttribute("orderLines", orderLines);
    }
}
