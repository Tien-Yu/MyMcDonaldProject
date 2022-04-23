/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.thymeleaftools;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Nathan
 */
@Configuration
public class ThymeleafConfiguration {
    @Bean
    public CollectionToolsDialect CollectionToolsDialect(){
        return new CollectionToolsDialect();
    }
}
