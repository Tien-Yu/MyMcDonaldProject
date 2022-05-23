/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.springmcdonald.repository;

import com.example.springmcdonald.pojo.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Nathan
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

    public List<Product> findByCategory(String category);

    @Query(value = "SELECT p FROM Product p WHERE p.category <> ?1 AND p.category <> ?2")
    public List<Product> findAllExcluding(String param1, String param2);
        
    public Page<Product> findAllByCategoryNot(String exclude, Pageable pageable);
    

}
