/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.service;

import com.example.springmcdonald.pojo.OrderLine;
import com.example.springmcdonald.repository.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nathan
 */
@Service
public class OrderLineService {
    @Autowired
    OrderLineRepository orderLineRepository;
    
    public int insert(OrderLine orderLine){
        return orderLineRepository.save(orderLine).getId();        
    }
    
    public void remove(int id){
        orderLineRepository.deleteById(id);
    }
    
}
