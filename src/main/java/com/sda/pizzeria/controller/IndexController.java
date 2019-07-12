package com.sda.pizzeria.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }


    @GetMapping("/register")
    public String getRegisterPage() {
        return "authorization/register";
    }


    @GetMapping("/pizza")
    public String getPizzaPage(){
        return "pizza";
    }

}


