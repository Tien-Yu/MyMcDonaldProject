/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springmcdonald.controller;

import com.example.springmcdonald.pojo.OrderLine;
import com.example.springmcdonald.pojoform.OrderLineForm;
import com.example.springmcdonald.pojo.Product;
import com.example.springmcdonald.pojoform.SelectionForm;
import com.example.springmcdonald.service.OrderLineService;
import com.example.springmcdonald.service.ProductService;
import com.example.springmcdonald.webtools.OrderLineTools;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Nathan
 */
@Controller
@RequestMapping("/menu")
public class ProductController {

    @Autowired
    ProductService prodService;
    @Autowired
    OrderLineService orderLineService;

    /*
        redirect
     */
    @GetMapping("")
    public String homepage(Model m) {
        return share_menu(m);
    }

    @GetMapping("/share")
    public String share_menu(Model m) {
        m.addAttribute("list", prodService.findByCategory("SHARE"));
        return "ProductHome";
    }

    @GetMapping("/main_course")
    public String main_course(Model m) {
        m.addAttribute("list", prodService.findByCategory("MAIN"));
        return "Main_Course";
    }

    @GetMapping("/sub_course")
    public String sub_course(Model m) {
        m.addAttribute("list", prodService.findByCategory("SUB"));
        return "Sub_Course";
    }

    @GetMapping("/drinks")
    public String drinks(Model m) {
        m.addAttribute("list", prodService.findByCategory("DRINK"));
        return "Drinks";
    }

    @GetMapping("/dessert")
    public String dessert(Model m) {
        m.addAttribute("list", prodService.findByCategory("DESSERT"));
        return "Dessert";
    }

    /**
     * 加入購物車前的確認(加上網址列參數用來判斷產品數量)
     *
     * @param id 產品編號
     * @param count 產品數量計算器
     * @param countsp 套餐專用數量計算器
     * @param session 確認有無地址存在
     * @param m 將產品傳給前端UI
     * @return
     */
    @GetMapping("/{id}/confirmInfo")
    public String confirmInfo(@PathVariable("id") int id,
            @RequestParam(defaultValue = "0") int count,
            @RequestParam(defaultValue = "0") int countsp,
            HttpSession session,
            Model m) {

        if (count < 0) {
            count = 0;
        }
        if (countsp < 0) {
            countsp = 0;
        }
        //以名字為用戶認證的資訊 -> 存在時即地址也同樣存在, 及不需要進稻填地址的頁面
        if (session.getAttribute("name") == null) {
            if (session.getAttribute("address") == null) {
                return "ConfirmAddress";
            }
        }

        Optional<Product> tmpProd = prodService.findById(id);

        List<Product> subProd = prodService.findByCategory("SUB");
        List<Product> drinkProd = prodService.findByCategory("DRINK");

        m.addAttribute("product", tmpProd.isPresent() ? tmpProd.get() : null);

        m.addAttribute("sub", subProd);
        m.addAttribute("drink", drinkProd);

        m.addAttribute("count", count);
        m.addAttribute("countsp", countsp);

        return "confirmInfo";
    }

    /**
     *
     * @param id 產品編號
     * @param orderLineForm 暫存下單的產品的基本資訊
     * @param selectionForm 暫存下單的產品的類型用來改變處理方式
     * @param session 儲存訂單資訊
     * @param m 當數量為0時傳給confirmInfo方法用
     * @return
     */
    @PostMapping("/shoppingcart")
    public String shoppingCart(int id, OrderLineForm orderLineForm, SelectionForm selectionForm, HttpSession session, Model m) {
        Product product = prodService.findById(id).get();
        int price = product.getPrice();
        int count = orderLineForm.getCount();     //可以用來代表單點(分享餐也同樣使用count來記數)
        int countsp = orderLineForm.getCountsp(); //可以用來代表套餐
        int amount = count + countsp;

        //if form data recive 0 amount, redirect back to confirmInfo page with same product
        if (amount == 0) {
            return confirmInfo(id, 0, 0, session, m);
        }

        /* 轉換成 Queue - 需使用 Queue 的 pull() 方法來達成分割作用 */
        Queue<String> selectionQueue = null;
        if (selectionForm.getSelection() != null) { //確保 selection 不為 null
            List<String> selectionList = selectionForm.getSelection();
            selectionQueue = new LinkedList();
            selectionQueue.addAll(selectionList);
        }


        /* SHARE 的判斷*/
        if (selectionForm.getCourse_type() != null) {
            System.out.println(selectionForm.getCourse_type());
            OrderLineTools.orderLineDivider_share(selectionQueue, price, count, product, orderLineService, session);
        }

        /*單點或附餐單點區段*/
        //如果一次的下單有套餐+單點 會先經過這邊處理單點
        if (count != 0 && selectionForm.getCourse_type() == null) {
            System.out.println(selectionForm.getCourse_type());
            OrderLineTools.orderLineDivider(price, count, product, orderLineService, session);
        }

        /*套餐區段*/
        //上面處理完單點後如果有countsp會接著處理套餐的部分
        if (countsp != 0) {
            System.out.println(selectionForm.getCourse_type());
            OrderLineTools.orderLineDivider_course(selectionQueue, price, countsp, product, orderLineService, session);
        }

        return "ShoppingCart";
    }

    /**
     * PostMapping 沒辦法直接透過網址列url存取
     *
     * @param session
     * @return
     */
    @GetMapping("/shoppingcart")
    public String shoppingCart(HttpSession session) {
        return "ShoppingCart";
    }

    @GetMapping("/{id}/remove")
    public String removeProduct(@PathVariable int id, HttpSession session) {
        List<OrderLine> oldList = (List) session.getAttribute("orderLines");
        List<OrderLine> newList = oldList.stream().filter(o -> o.getId() != id).collect(Collectors.toList());
        orderLineService.remove(id);
        if(newList.isEmpty()){
           session.removeAttribute("orderLines");
           return "redirect:/menu/shoppingcart";
        }
        session.setAttribute("orderLines", newList);
        return "redirect:/menu/shoppingcart";
    }

}
