/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.controller;

import com.example.springmcdonald.pojo.Product;
import com.example.springmcdonald.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nathan
 */
@Controller
@RequestMapping("/menu")
public class ProductController {
    @Autowired
    ProductService prodService;
    
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
    
    /*
        cart function
    */
    @GetMapping("/{id}/confirmInfo")
    public String confirmInfo(@PathVariable("id") int id, Model m){
        Optional<Product> tmpProd = prodService.findById(id);
        m.addAttribute("product", tmpProd.isPresent()?tmpProd.get():null);        
        return "confirmInfo";
    }

}
