package com.petedillo.api.controller;

import com.petedillo.api.model.BlogPost;
import com.petedillo.api.model.SearchResponseDTO;
import com.petedillo.api.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {
        org.springframework.web.bind.annotation.RequestMethod.GET,
        org.springframework.web.bind.annotation.RequestMethod.POST,
        org.springframework.web.bind.annotation.RequestMethod.PUT,
        org.springframework.web.bind.annotation.RequestMethod.DELETE,
        org.springframework.web.bind.annotation.RequestMethod.OPTIONS
})
public class ApiController {

    private final BlogPostService blogPostService;
    private final DataSource dataSource;

    @Autowired
    public ApiController(BlogPostService blogPostService, DataSource dataSource) {
        this.blogPostService = blogPostService;
        this.dataSource = dataSource;
    }

    @GetMapping("health")
    public ResponseEntity<String> healthCheck() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) { // Check if connection is valid within 2 second
                return ResponseEntity.ok("Connected to DB");
            } else {
                return ResponseEntity.status(500).body("Not connected to DB: Invalid connection");
            }
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Not connected to DB: " + e.getMessage());
        }
    }

    @GetMapping("posts")
    public ResponseEntity<List<BlogPost>> getPosts() {
        return ResponseEntity.ok(blogPostService.getAllPosts());
    }

    @GetMapping("search")
    public ResponseEntity<SearchResponseDTO> searchPosts(@RequestParam String searchTerm) {
        List<BlogPost> results = blogPostService.searchPosts(searchTerm);
        SearchResponseDTO response = new SearchResponseDTO(searchTerm, results);
        return ResponseEntity.ok(response);
    }

}
