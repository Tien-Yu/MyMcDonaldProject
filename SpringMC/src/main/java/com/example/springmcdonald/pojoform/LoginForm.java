/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.pojoform;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Nathan
 */
@Data
public class LoginForm {
    @NotBlank
    @Length(max = 20, min = 4)
    private String userName;
    @NotBlank
    @Length(max = 20, min = 6)
    private String password;
}
