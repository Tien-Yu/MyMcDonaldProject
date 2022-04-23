/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.thymeleaftools;

import com.example.springmcdonald.pojo.OrderLine;
import java.util.List;

/**
 *
 * @author Nathan
 */
public final class CollectionTool{
    
    public int summarization(List<OrderLine> list) throws Exception{
        if(list == null){
            return 0;
        }
//        Class c = Class.forName("com.example.springmcdonald.pojo.OrderLine");
//        Method m = c.getDeclaredMethod("getPurchasePrice");
        Integer total = list.stream().map(o -> o.getPurchasePrice()).reduce(0, (a, b) -> a+b);

        return total;
        
    }
    
}
