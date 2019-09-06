package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MathController {

    @GetMapping("/add/{a}/and/{b}")
    @ResponseBody
    public long add(@PathVariable long a, @PathVariable long b) {
        return a + b;
    }

    @GetMapping("/subtract/{a}/from/{b}")
    @ResponseBody
    public String subtract(@PathVariable Long a, @PathVariable Long b) {
        return Long.toString(b-a);
    }

    @GetMapping("/multiply/{a}/and/{b}")
    @ResponseBody
    public String multiply(@PathVariable String a, @PathVariable String b) {
        return Long.toString(Long.parseLong(a) * new Long(b));
    }

    @RequestMapping(path="/divide/{a}/by/{b}", method = RequestMethod.GET)
    @ResponseBody
    public String divide(@PathVariable double a, @PathVariable double b ) {
        return a + " divided by " + b + " = " + (a / b);
    }

}
