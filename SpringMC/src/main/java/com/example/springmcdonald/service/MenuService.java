/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.service;

import com.example.springmcdonald.pojo.Menus;
import com.example.springmcdonald.repository.MenuRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nathan
 */
@Service
public class MenuService {
    @Autowired
    MenuRepository menuRepository;
    
    public Optional<Menus> findById(int id){
        return menuRepository.findById(id);
    }
    
}
