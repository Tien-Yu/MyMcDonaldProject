/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.controller;

import com.example.springmcdonald.pojo.Product;
import com.example.springmcdonald.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @GetMapping("")
    public String homepage(){
        return share_menu();
    }
    
    @GetMapping("/share")
    public String share_menu(){
        return "ProductHome";
    }
    
    
    @GetMapping("/combo")
    public String combo_menu(){
        return "Combo_Menu";
    }
    
    
    @GetMapping("/main_course")
    public String main_course(Model m){
        m.addAttribute("list", prodService.listAll());        
        return "Main_Course";
    }
    
    @GetMapping("/drinks")
    public String drinks(){
        return "Drinks";
    }

}
