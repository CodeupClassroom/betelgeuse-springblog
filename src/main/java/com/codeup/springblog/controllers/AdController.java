package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Ad;
import com.codeup.springblog.repos.AdRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdController {

    private final AdRepository adDao;

    public AdController(AdRepository adRepository){
        adDao = adRepository;
    }

    @GetMapping("/ads")
    public String index(Model vModel) {
        Iterable<Ad> ads = adDao.findAll();
        vModel.addAttribute("ads", ads);
        return "ads/index";
    }

    @GetMapping("/ads/{id}")
    public String show(@PathVariable long id, Model viewModel) {
        Ad ad = adDao.findOne(id);
        viewModel.addAttribute("ad", ad);
        return "ads/show";
    }

    @GetMapping("/ads/search")
    public String show(@RequestParam(name = "term") String term, Model viewModel) {
        List<Ad> ads = adDao.searchByTitleLike(term);
        viewModel.addAttribute("ads", ads);
        return "ads/index";
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
