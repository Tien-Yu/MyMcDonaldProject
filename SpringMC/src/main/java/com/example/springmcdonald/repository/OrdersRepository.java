/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.springmcdonald.repository;

import com.example.springmcdonald.pojo.Orders;
import com.example.springmcdonald.pojo.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nathan
 */
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    public List<Orders> findByStatus(String status);
   
    //users status (non users -> null)
    public List<Orders> findByUsersAndStatus(Users users, String status);
        
}
