/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.controller;

import com.example.springmcdonald.pojoform.LoginForm;
import com.example.springmcdonald.pojoform.UsersForm;
import com.example.springmcdonald.service.UsersService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author timothy
 */
@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersService usersService;    
    /*
        redirect    
    */       
    @GetMapping("/account")
    public String usersHome(){
        
        
        return "UsersHome";
    }
    @GetMapping("/register")
    public String register(Model m){
        m.addAttribute("usersform", new UsersForm());        
        return "Register";
    }
    @GetMapping("/login")
    public String login(Model m){
        m.addAttribute("loginForm", new LoginForm());
        return "Login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        
        
        return "ProductHome";
    }
    @GetMapping("/confirmAddress")
    public String confirmAddress(){
        
        
        return "ConfirmAddress";
    }
    
    
    /*
        Post
        spring validation dependencies needed
    */
    
    /**
     * save user infomation to database
     * user(usersform) vvalidation required
     * @param usersForm
     * @param br
     * @return 
     */
    @PostMapping("/registerSave")
    public String registerSave(@Valid UsersForm usersForm ,BindingResult br){
        
        
        return "UsersHome";
    }
    
    /**
     * user(loginform) validation required
     * @param lg
     * @param br
     * @param m
     * @return 
     */
    @PostMapping("/loginPost")
    public String loginPost(@Valid LoginForm lg ,BindingResult br, Model m){
//        m.addAttribute(loginForm); O
//        m.addAttribute("loginform", loginForm); X Model 的物件中只有以駱駝式命名的名稱才能取得BindingResult中的錯誤資訊
        m.addAttribute("loginForm", lg);
        if(br.hasErrors()){
            return "Login";
        }
        
        return "ProductHome";
    }
    
    
    
    
    
    
    
}
