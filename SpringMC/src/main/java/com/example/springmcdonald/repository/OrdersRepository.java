/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.springmcdonald.repository;

import com.example.springmcdonald.pojo.Orders;
import com.example.springmcdonald.pojo.Users;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Nathan
 */
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    //non-users(status is not completed)
    @Query(value = "SELECT o FROM Orders o WHERE o.trackingNumber = ?1 AND o.status <> ?2")
    public List<Orders> findByTrackingNumberAndStatusExcluding(String phone, String status);
   
    //login users(status is not completed)
    @Query(value = "SELECT o FROM Orders o WHERE o.users = ?1 AND o.status <> ?2")
    public List<Orders> findByUsersAndStatusExcluding(Users users, String status);
    
    //find all history with paging and sorting
    public Page<Orders> findAllByUsers(Users users, Pageable pageable);
        
}
