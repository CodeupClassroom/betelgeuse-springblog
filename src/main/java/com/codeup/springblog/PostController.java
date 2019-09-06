package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {


//    GET	/posts	posts index page
    @GetMapping("/posts")
    @ResponseBody
    public String postsIndex() {
        return "<h2>Here is a list of the posts</h2>"+
                "<ul>" +
                "<li>1</li>" +
                "<li>2</li>" +
                "<li>3</li>" +
                "</ul>";
    }

//    GET	/posts/{id}	view an individual post
    @GetMapping("/posts/{id}")
    @ResponseBody
    public String postDetail(@PathVariable long id) {
        return "Here is post #" + id;
    }

//    GET	/posts/create	view the form for creating a post
    @GetMapping("/posts/create")
    @ResponseBody
    public String createPostForm() {
        return "Please fill out this form";
    }

//    POST	/posts/create	create a new post
    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost() {
        return "Great new Post";
    }
}
