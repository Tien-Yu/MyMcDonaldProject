/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.pojo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import lombok.Data;


/**
 *
 * @author Nathan
 */

@Entity
@Data
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Calendar orderdate;
    private String status;
    
    //訂單對應的客戶
    @ManyToOne
    private Users users;    
       
    private String trackingNumber; //phone number
    
    //訂單資訊 one to many
    @OneToMany(mappedBy = "orders", cascade = CascadeType.REMOVE)
    private List<OrderLine> orderLines;
    
   
    @PrePersist
    private void preInsert(){
        if(this.status == null){
            this.status = "received";
        }
    }
    
}
