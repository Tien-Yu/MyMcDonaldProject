/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.controller;

import com.example.springmcdonald.pojo.Users;
import com.example.springmcdonald.pojoform.LoginForm;
import com.example.springmcdonald.pojoform.UsersForm;
import com.example.springmcdonald.service.UsersService;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author timothy
 */
@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    /*
        redirect    
     */
    @GetMapping("/account")
    public String usersHome(HttpSession session, Model m) {
        String name = (String) session.getAttribute("name");
        Optional<Users> users = usersService.findByName(name);
        if (users.isPresent()) {
            UsersForm usersForm = new UsersForm();
            usersForm.setId(users.get().getId()); //新增欄位
            usersForm.setUserName(users.get().getUserName());
            usersForm.setPassword(users.get().getPassword());
            usersForm.setConfirmPassword("");
            usersForm.setUserEmail(users.get().getUserEmail());
            usersForm.setAddress(users.get().getAddress());
            m.addAttribute("usersForm", usersForm);
        }
        m.addAttribute("reset", "yes");

        return "UsersHome";
    }

    @GetMapping("/register")
    public String register(Model m) {
        m.addAttribute("usersForm", new UsersForm());
        return "Register";
    }

    @GetMapping("/login")
    public String login(Model m) {
        m.addAttribute("loginForm", new LoginForm());
        return "Login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/menu";
    }

    @GetMapping("/confirmAddress")
    public String confirmAddress() {
        return "ConfirmAddress";
    }

    /*
        Post
        spring validation dependencies needed
     */
    /**
     * save user infomation to database user(usersform) vvalidation required
     *
     * @param usersForm
     * @param br
     * @return
     */
    @PostMapping("/registerSave")
    public String registerSave(@Valid UsersForm usersForm, BindingResult br) {
        if (!usersForm.matchPassword()) {
            br.rejectValue("confirmPassword", "Match", "重複輸入密碼錯誤!");
        }

        if (br.hasErrors()) {
            return "Register";
        }

        Users tmpUsers = usersForm.convertToUsers();
        usersService.insert(tmpUsers);

        return "redirect:/users/login";
    }

    /**
     * user(loginform) validation required
     *
     * @param lg
     * @param br
     * @param session
     * @param m
     * @return
     */
    @PostMapping("/loginPost")
    public String loginPost(@Valid LoginForm lg, BindingResult br, HttpSession session, Model m) {
//        m.addAttribute(loginForm); O
//        m.addAttribute("loginform", loginForm); X Model 的物件中只有以駱駝式命名的名稱才能取得BindingResult中的錯誤資訊
        m.addAttribute("loginForm", lg);
        if (br.hasErrors()) {
            return "Login";
        }

        String name = lg.getUserName();
        String password = lg.getPassword();
        Optional<Users> tmpUser = usersService.findByNameAndPassword(name, password);
        if (tmpUser.isPresent()) {
            session.setAttribute("name", name);
            return "redirect:/menu";
        }

        br.rejectValue("userName", "NoMatch", "帳號可能不正確");
        br.rejectValue("password", "NoMatch", "密碼可能不正確");
        return "Login";
    }

    //最後需要從ProductController中處理下單時確認有無地址的動作
    /**
     * session 中要加入完整的address
     *
     * @param address
     * @param floor
     * @param room
     * @param comment
     * @param session
     * @return
     */
    @PostMapping("/addressPost")
    public String addressPost(String address, String floor, String room, String comment, HttpSession session) {
        if (address.length() < 10) {
            return "ConfirmAddress";
        }

        if (!floor.equals("")) {
            address = address.concat(floor + "樓");
        }
        if (!room.equals("")) {
            address = address.concat(room + "號房");
        }

        session.setAttribute("address", address);

        if (!comment.equals("")) {
            session.setAttribute("comment", comment);
        }

        return "redirect:/menu";
    }

    /**
     *
     * @param usersForm
     * @param br
     * @param session
     * @param m
     * @param redirectAttributes 橫跨POST將資料傳給其他頁面
     * @return
     */
    @PostMapping("/accountPost")
    public String accountPost(@Valid UsersForm usersForm, BindingResult br, HttpSession session, Model m, RedirectAttributes redirectAttributes) {
        m.addAttribute("usersForm", usersForm);
        if (!usersForm.matchPassword()) {
            br.rejectValue("confirmPassword", "Match", "重複輸入密碼錯誤!");
        }

        if (br.hasErrors()) {
            //交由前端Javascript判斷是否需要保留變更html元素的屬性
            m.addAttribute("reset", "no");
            return "UsersHome";
        }

        //Session 與 資料庫更新資訊處理邏輯
        Users tmpUsers = usersForm.convertToUsers();
        String name = tmpUsers.getUserName();
        session.setAttribute("name", name);

        usersService.insert(tmpUsers);

        redirectAttributes.addFlashAttribute("message", "資料更新成功!!!");

        return "redirect:/users/account";
    }

}
