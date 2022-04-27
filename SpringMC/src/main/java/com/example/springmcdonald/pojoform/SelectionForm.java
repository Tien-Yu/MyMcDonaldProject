/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.pojoform;

import java.util.List;
import java.util.Queue;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Nathan
 */
@Data
@NoArgsConstructor
public class SelectionForm {
    private String course_type; //only null or share
    private List<String> selection; 
}
