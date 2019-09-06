package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String welcome(Model viewModel)
    {
        String userSession = "ana";
        String role = "mortal";

        List<String> colors = new ArrayList<>();

        colors.add("blue");
        colors.add("red");
        colors.add("green");
        colors.add("white");

        if(userSession.equals("fer")){
            role = "admin";
        }
        viewModel.addAttribute("role", role);
        viewModel.addAttribute("colors" , colors);
        return "home";
    }
}
