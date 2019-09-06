package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String welcome(Model viewModel)
    {
        String userSession = "ana";
        String role = "mortal";
        if(userSession.equals("fer")){
            role = "admin";
        }
        viewModel.addAttribute("role", role);
        return "home";
    }
}
