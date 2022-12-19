package com.example.easy_flow_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("admin")
public class AdminController {

    @GetMapping("index")
    public String index(){
        return "admin/index";
    }
}
