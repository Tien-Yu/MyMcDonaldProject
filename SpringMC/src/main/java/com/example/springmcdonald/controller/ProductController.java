/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.controller;

import com.example.springmcdonald.pojo.OrderLine;
import com.example.springmcdonald.pojoform.OrderLineForm;
import com.example.springmcdonald.pojo.Product;
import com.example.springmcdonald.service.OrderLineService;
import com.example.springmcdonald.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Nathan
 */
@Controller
@RequestMapping("/menu")
public class ProductController {
    @Autowired
    ProductService prodService;
    @Autowired
    OrderLineService orderLineService;            
    
    
    /*
        redirect
    */
    @GetMapping("")
    public String homepage(Model m){
        return share_menu(m);
    }
    
    @GetMapping("/share")
    public String share_menu(Model m){
        m.addAttribute("list", prodService.findByCategory("SHARE"));
        return "ProductHome";
    }
        
    @GetMapping("/main_course")
    public String main_course(Model m){
        m.addAttribute("list", prodService.findByCategory("MAIN"));               
        return "Main_Course";
    }
    
    @GetMapping("/sub_course")
    public String sub_course(Model m){
        m.addAttribute("list", prodService.findByCategory("SUB"));        
        return "Sub_Course";
    }
    
    @GetMapping("/drinks")
    public String drinks(Model m){
        m.addAttribute("list", prodService.findByCategory("DRINK"));
        return "Drinks";
    }
    
    @GetMapping("/dessert")
    public String dessert(Model m){
        m.addAttribute("list", prodService.findByCategory("DESSERT"));
        return "Dessert";
    }
    
    /**
     * 加入購物車前的確認(加上網址列參數用來判斷產品數量)
     * @param id 產品編號
     * @param count 產品數量計算器
     * @param countsp 套餐專用數量計算器
     * @param m 將產品傳給前端UI
     * @return 
     */
    
    @GetMapping("/{id}/confirmInfo")
    public String confirmInfo(@PathVariable("id") int id,
                              @RequestParam(defaultValue = "0") int count,
                              @RequestParam(defaultValue = "0") int countsp,
                              Model m){
        if(count < 0){
            count = 0;
        }
        if(countsp < 0){
            countsp = 0;
        }

        Optional<Product> tmpProd = prodService.findById(id);
        
        List<Product> subProd = prodService.findByCategory("SUB");
        List<Product> drinkProd = prodService.findByCategory("DRINK");
        
        m.addAttribute("product", tmpProd.isPresent()?tmpProd.get():null);
        
        m.addAttribute("sub", subProd);
        m.addAttribute("drink", drinkProd);
        
        m.addAttribute("count", count);
        m.addAttribute("countsp", countsp);
      
        return "confirmInfo";
    }
    
    /**   
     * @param id 產品編號
     * @param orderLineForm 暫存前端傳回來的資訊
     * @param session 儲存訂單資訊
     * @param m 當數量為0時傳給confirmInfo方法用
     * @return 
     */
    @PostMapping("/shoppingcart")
    public String shoppingCart(int id, OrderLineForm orderLineForm, HttpSession session, Model m){        
        Optional<Product> tmpProd = prodService.findById(id);                
        int price = tmpProd.get().getPrice();
        int count = orderLineForm.getCount();
        int countsp = orderLineForm.getCountsp();
        int amount = count + countsp;
        
        //if the form data amount is 0, redirect to confirmInfo page and show the same product
        if(amount == 0){
            return confirmInfo(id, 0, 0, m);
        }
        
        orderLineForm.setAmount(amount);
        orderLineForm.setPurchasePrice(price*count + (price+68)*countsp);
        orderLineForm.setProduct(tmpProd.get());
        
        //convert OrderLineForm to OrderLine
        OrderLine orderLine = orderLineForm.convertToOrderLine();
        
        //insert record to database
        orderLineService.insert(orderLine);
        
        //get the list of OrderLine, if not exists then create one        
        List<OrderLine> orderLines = (List)session.getAttribute("orderLines");
        if(orderLines == null){
            orderLines = new ArrayList();
            session.setAttribute("orderLines", orderLines);
        }        
        //add orderLine to the orderLine list
        orderLines.add(orderLine);
               
        session.setAttribute("orderLines", orderLines);
        return "ShoppingCart";
    }
    /**
     * PostMapping 沒辦法直接透過網址列url存取
     * @param session
     * @return 
     */
    @GetMapping("/shoppingcart")
    public String shoppingCart(HttpSession session){
        return "ShoppingCart";
    }
   
    @GetMapping("/{id}/remove")
    public String removeProduct(@PathVariable int id ,HttpSession session){
        List<OrderLine> oldList = (List)session.getAttribute("orderLines");
        List<OrderLine> newList = oldList.stream().filter(o -> o.getId() != id).collect(Collectors.toList());
        orderLineService.remove(id);
        session.setAttribute("orderLines", newList);
//        return shoppingCart(session);
        return "redirect:/menu/shoppingcart";
    }
  
}
