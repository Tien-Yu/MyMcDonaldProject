/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.pojoform;

import com.example.springmcdonald.pojo.Users;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

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
    
    public Users convertToUsers(){
        return new UsersFormConvert().convertTo(this);
    }
    
    
    private class UsersFormConvert implements IFormConvert<UsersForm, Users>{

        @Override
        public Users convertTo(UsersForm s) {
            Users tmpUsers = new Users();
            BeanUtils.copyProperties(s, tmpUsers);
            return tmpUsers;
        }
        
    }
    
}
