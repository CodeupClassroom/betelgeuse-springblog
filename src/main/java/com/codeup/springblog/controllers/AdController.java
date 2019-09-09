package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Ad;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class AdController {

    @GetMapping("/ads")
    public String index(Model vModel) {
        ArrayList<Ad> consoles = new ArrayList<>();

        Ad ps4 = new Ad("ps4", "brand new");
        Ad switchNintendo = new Ad("switch", "used");
        Ad threeDs = new Ad("3ds", "old");

        consoles.add(ps4);
        consoles.add(switchNintendo);
        consoles.add(threeDs);

        vModel.addAttribute("ads", consoles);

        return "ads/index";
    }

    @GetMapping("/ads/{id}")
    public String show(@PathVariable long id, Model viewModel) {
        Ad ad = new Ad("ps4", "brand new");
        viewModel.addAttribute("ad", ad);
        return "ads/show";
    }

//    @GetMapping("/posts/create")
//    @ResponseBody
//    public String createPostForm() {
//        return "Please fill out this form";
//    }
//
////    POST	/posts/create	create a new post
//    @PostMapping("/posts/create")
//    @ResponseBody
//    public String createPost() {
//        return "Great new Post";
//    }
}
