/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.pojoform;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Nathan
 */
@Data
public class UsersForm {
    @NotBlank
    @Length(max = 20)
    private String userName;
    
    @NotBlank
    @Length(max = 20, min = 6)
    @Pattern(regexp ="^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "必須包含一個特殊符號")
    private String password;
    
    @NotBlank
    @Length(max = 20, min = 6)
    private String confirmPassword;
    
    @NotBlank
    @Email    
    private String userEmail;
    
    @NotBlank    
    private String address;
    
    public boolean matchPassword(){
        return password.equals(confirmPassword);
    }
}
