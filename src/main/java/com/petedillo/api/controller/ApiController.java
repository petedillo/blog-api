package com.petedillo.api.controller;


import com.petedillo.api.model.BlogPost;
import com.petedillo.api.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ApiController {

    private final BlogPostService blogPostService;

    @Autowired
    public ApiController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Health Check");
    }

    @GetMapping("posts")
    public ResponseEntity<List<BlogPost>> getPosts() {
        return ResponseEntity.ok(blogPostService.getAllPosts());
    }

    @GetMapping("search")
    public ResponseEntity<String> searchPosts() {
        return ResponseEntity.ok("Search");
    }

}
