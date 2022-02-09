/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.springmcdonald.repository;

import com.example.springmcdonald.pojo.Menus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nathan
 */
public interface MenuRepository extends JpaRepository<Menus, Integer> {
    
}
