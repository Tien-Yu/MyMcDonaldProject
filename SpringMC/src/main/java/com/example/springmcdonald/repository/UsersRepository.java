/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.springmcdonald.repository;

import com.example.springmcdonald.pojo.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author timothy
 */
public interface UsersRepository extends JpaRepository<Users, Integer>{
    
    public Optional<Users> findByUserName(String userName);
    public Optional<Users> findByUserNameAndPassword(String userName, String password);
    
}
