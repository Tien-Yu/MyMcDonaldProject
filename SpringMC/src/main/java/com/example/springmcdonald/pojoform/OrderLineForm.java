/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.pojoform;

import com.example.springmcdonald.pojo.OrderLine;
import com.example.springmcdonald.pojo.Product;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author Nathan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineForm{

    private Product product;
    private int amount;    
    private int purchasePrice;

    private int count;
    private int countsp;
    
    //物件呼叫方法 - deprecated
    public OrderLine convertToOrderLine(){
        return new OrderLineFormConvert().convertTo(this);
    }
    
    //內部類別處理轉換邏輯 - deprecated
    private class OrderLineFormConvert implements IFormConvert<OrderLineForm, OrderLine>{
        @Override
        public OrderLine convertTo(OrderLineForm s) {
            OrderLine tmpOrderLine = new OrderLine();
            BeanUtils.copyProperties(s, tmpOrderLine);
            return tmpOrderLine;
        }
    }
            
            
}
