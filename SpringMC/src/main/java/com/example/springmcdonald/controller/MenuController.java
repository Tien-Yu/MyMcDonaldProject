/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.controller;

import com.example.springmcdonald.pojo.Menus;
import com.example.springmcdonald.service.MenuService;
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
@RequestMapping("/manytomany")
public class MenuController {
    @Autowired
    MenuService menuService;
    
    @GetMapping("/test/{id}")
    public String testmenu(@PathVariable int id, Model m){
        Optional<Menus> tmpmenu = menuService.findById(id);
        
        m.addAttribute("menu", tmpmenu.isPresent()? tmpmenu.get():null);
        return "TestMenu";
    }
    
}
