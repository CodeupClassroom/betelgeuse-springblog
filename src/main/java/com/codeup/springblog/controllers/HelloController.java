package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
class HelloController {

    private long count = 0;

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name, Model viewModel) {
        viewModel.addAttribute("name", name);
        return "hello";
    }

    @PostMapping("/hello")
    @ResponseBody
    public String goodbye() {
        return "Goodbye from Spring";
    }

    @PostMapping("/goodbye")
    @ResponseBody
    public String goodbyeGoodbye() {
        return "You waved goodbye";
    }

    @RequestMapping(path = "/increment/{number}", method = RequestMethod.GET)
    @ResponseBody
    public String addOne(@PathVariable long number) {
        count += number;
        return "Our count is now at "+ count +"!";
    }

    @GetMapping("/join")
    public String showJoinForm() {
        return "join";
    }

    @PostMapping("/join")
    public String joinCohort(@RequestParam(name = "cohort") String cohort, Model model) {
        model.addAttribute("cohort", "Welcome to " + cohort + "!");
        return "join";
    }
}