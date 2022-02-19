/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.service;

import com.example.springmcdonald.pojo.Users;
import com.example.springmcdonald.repository.UsersRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author timothy
 */
@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;
    
    public Optional<Users> findById(int id){
        return usersRepository.findById(id);
    }
    
    public Optional<Users> findByName(String name){
        return usersRepository.findByUserName(name);
    }
    
    public void insert(Users users){
        usersRepository.save(users);
    }
    
    
}
