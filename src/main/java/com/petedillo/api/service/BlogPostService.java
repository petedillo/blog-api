package com.petedillo.api.service;

import com.petedillo.api.exception.ResourceNotFoundException;
import com.petedillo.api.model.BlogPost;
import com.petedillo.api.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    @Autowired
    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    public BlogPost getPostBySlug(String slug) {
        return blogPostRepository.findBySlug(slug)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with slug: " + slug));
    }

    public List<BlogPost> searchPosts(String searchTerm) {
        return blogPostRepository.searchByTitleOrSlug(searchTerm);
    }
}
