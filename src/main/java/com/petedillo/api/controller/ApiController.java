package com.petedillo.api.controller;

import com.petedillo.api.config.AppConfig;
import com.petedillo.api.model.BlogPost;
import com.petedillo.api.model.SearchResponseDTO;
import com.petedillo.api.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final AppConfig appConfig;

    @Autowired
    public ApiController(BlogPostService blogPostService, DataSource dataSource, AppConfig appConfig) {
        this.blogPostService = blogPostService;
        this.dataSource = dataSource;
        this.appConfig = appConfig;
    }

    @GetMapping("health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("environment", appConfig.getEnvironment());
        health.put("version", appConfig.getVersion());
        health.put("timestamp", LocalDateTime.now());

        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) {
                health.put("database", "connected");
                List<BlogPost> posts = blogPostService.getAllPosts();
                health.put("postsCount", posts.size());
            } else {
                health.put("database", "disconnected");
                health.put("error", "Invalid connection");
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(health);
            }
        } catch (SQLException e) {
            health.put("database", "disconnected");
            health.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(health);
        }

        return ResponseEntity.ok(health);
    }

    @GetMapping("posts")
    public ResponseEntity<List<BlogPost>> getPosts() {
        return ResponseEntity.ok(blogPostService.getAllPosts());
    }

    @GetMapping("posts/{slug}")
    public ResponseEntity<BlogPost> getPostBySlug(@PathVariable String slug) {
        BlogPost post = blogPostService.getPostBySlug(slug);
        return ResponseEntity.ok(post);
    }

    @GetMapping("info")
    public ResponseEntity<Map<String, String>> getInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("environment", appConfig.getEnvironment());
        info.put("version", appConfig.getVersion());
        info.put("name", "Blog API");
        return ResponseEntity.ok(info);
    }

    @GetMapping("search")
    public ResponseEntity<SearchResponseDTO> searchPosts(@RequestParam String searchTerm) {
        List<BlogPost> results = blogPostService.searchPosts(searchTerm);
        SearchResponseDTO response = new SearchResponseDTO(searchTerm, results);
        return ResponseEntity.ok(response);
    }

}
