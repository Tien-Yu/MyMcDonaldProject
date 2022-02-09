/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.pojo;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;

/**
 *
 * @author Nathan
 */
@Entity
@Data
public class Menus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String menu_name;
    
    @ManyToMany
//    @JoinTable(name="menu_wine", joinColumns = @JoinColumn(name="menu_id"), 
//                                 inverseJoinColumns = @JoinColumn(name="wine_id"))
    private List<Wine> wines;
    
    
}
