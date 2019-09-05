package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
class HelloController {

    private long count = 0;

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "<h2>Hello from Spring!</h2>";
    }
    @GetMapping("/hello/{name}")
    @ResponseBody
    public String sayHello(@PathVariable String name) {
        return "<h1>Hello " + name + "!</h1>";
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
}