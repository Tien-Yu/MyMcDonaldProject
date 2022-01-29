/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.springmcdonald.repository;

import com.example.springmcdonald.pojo.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author Nathan
 */
public interface ProductRepository extends JpaRepository<Product, Integer>{
    
    public List<Product> findByCategory(String category);

}
