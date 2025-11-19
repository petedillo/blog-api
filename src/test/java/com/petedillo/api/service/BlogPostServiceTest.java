package com.petedillo.api.service;

import com.petedillo.api.exception.ResourceNotFoundException;
import com.petedillo.api.model.BlogPost;
import com.petedillo.api.repository.BlogPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceTest {

    @Mock
    private BlogPostRepository blogPostRepository;

    @InjectMocks
    private BlogPostService blogPostService;

    private BlogPost testPost;

    @BeforeEach
    void setUp() {
        testPost = new BlogPost();
        testPost.setId(1L);
        testPost.setTitle("Test Post");
        testPost.setSlug("test-post");
        testPost.setContent("Test content");
        testPost.setExcerpt("Test excerpt");
        testPost.setStatus("published");
        testPost.setPublishedAt(LocalDateTime.now());
    }

    @Test
    void testGetAllPosts_ReturnsListOfPosts() {
        // Arrange
        List<BlogPost> posts = Arrays.asList(testPost);
        when(blogPostRepository.findAll()).thenReturn(posts);

        // Act
        List<BlogPost> result = blogPostService.getAllPosts();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Post", result.get(0).getTitle());
        verify(blogPostRepository).findAll();
    }

    @Test
    void testGetAllPosts_EmptyList_ReturnsEmptyList() {
        // Arrange
        when(blogPostRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<BlogPost> result = blogPostService.getAllPosts();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(blogPostRepository).findAll();
    }

    @Test
    void testGetPostBySlug_ValidSlug_ReturnsPost() {
        // Arrange
        when(blogPostRepository.findBySlug("test-post"))
            .thenReturn(Optional.of(testPost));

        // Act
        BlogPost result = blogPostService.getPostBySlug("test-post");

        // Assert
        assertNotNull(result);
        assertEquals("Test Post", result.getTitle());
        assertEquals("test-post", result.getSlug());
        verify(blogPostRepository).findBySlug("test-post");
    }

    @Test
    void testGetPostBySlug_InvalidSlug_ThrowsResourceNotFoundException() {
        // Arrange
        when(blogPostRepository.findBySlug("invalid-slug"))
            .thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> blogPostService.getPostBySlug("invalid-slug")
        );

        assertEquals("Post not found with slug: invalid-slug", exception.getMessage());
        verify(blogPostRepository).findBySlug("invalid-slug");
    }

    @Test
    void testSearchPosts_ValidTerm_ReturnsMatchingPosts() {
        // Arrange
        List<BlogPost> posts = Arrays.asList(testPost);
        when(blogPostRepository.searchByTitleOrSlug("test"))
            .thenReturn(posts);

        // Act
        List<BlogPost> result = blogPostService.searchPosts("test");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Post", result.get(0).getTitle());
        verify(blogPostRepository).searchByTitleOrSlug("test");
    }

    @Test
    void testSearchPosts_NoMatches_ReturnsEmptyList() {
        // Arrange
        when(blogPostRepository.searchByTitleOrSlug("nonexistent"))
            .thenReturn(Collections.emptyList());

        // Act
        List<BlogPost> result = blogPostService.searchPosts("nonexistent");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(blogPostRepository).searchByTitleOrSlug("nonexistent");
    }
}
