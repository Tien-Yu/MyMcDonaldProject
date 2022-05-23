/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.service;

import com.example.springmcdonald.pojo.Product;
import com.example.springmcdonald.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nathan
 */
@Service
public class ProductService {
    @Autowired
    ProductRepository prodRepository;
    
    public List<Product> findByCategory(String category){
        return prodRepository.findByCategory(category);        
    }
    
    public Optional<Product> findById(int id){
        return prodRepository.findById(id);
    }
    
    public List<Product> findAllExcluding(String param1, String param2){
        return prodRepository.findAllExcluding(param1, param2);
    }
    
    public Page<Product> findAllByCategoryNot(String exclude, int pageNo, int size){
        if(pageNo <= 1){
            pageNo = 1;
        }
        
        Pageable pageable = null;
                
        pageable = PageRequest.of(pageNo-1, size);
        Page<Product> pageProduct = prodRepository.findAllByCategoryNot(exclude, pageable);
        
        int totalPages = pageProduct.getTotalPages();
        if(pageNo >= totalPages){
            pageNo = pageProduct.getTotalPages();
        }
        
        pageable = PageRequest.of(pageNo-1, size);

        return prodRepository.findAllByCategoryNot(exclude, pageable);
    }
    
}
