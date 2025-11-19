package com.petedillo.api.repository;

import com.petedillo.api.model.BlogPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class BlogPostRepositoryTest {

    @Autowired
    private BlogPostRepository blogPostRepository;

    private BlogPost testPost;

    @BeforeEach
    void setUp() {
        blogPostRepository.deleteAll();

        testPost = new BlogPost();
        testPost.setTitle("Test Post");
        testPost.setSlug("test-post");
        testPost.setContent("Test content for the blog post");
        testPost.setExcerpt("Test excerpt");
        testPost.setStatus("published");
        testPost.setPublishedAt(LocalDateTime.now());
        testPost.setCreatedAt(LocalDateTime.now());
        testPost.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testFindAll_ReturnsAllPosts() {
        // Arrange
        blogPostRepository.save(testPost);

        // Act
        List<BlogPost> result = blogPostRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindBySlug_ValidSlug_ReturnsPost() {
        // Arrange
        blogPostRepository.save(testPost);

        // Act
        Optional<BlogPost> result = blogPostRepository.findBySlug("test-post");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Post", result.get().getTitle());
        assertEquals("test-post", result.get().getSlug());
    }

    @Test
    void testFindBySlug_InvalidSlug_ReturnsEmpty() {
        // Act
        Optional<BlogPost> result = blogPostRepository.findBySlug("nonexistent");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearchByTitleOrSlug_MatchingTitle_ReturnsPosts() {
        // Arrange
        blogPostRepository.save(testPost);

        // Act
        List<BlogPost> result = blogPostRepository.searchByTitleOrSlug("Test");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Post", result.get(0).getTitle());
    }

    @Test
    void testSearchByTitleOrSlug_MatchingSlug_ReturnsPosts() {
        // Arrange
        blogPostRepository.save(testPost);

        // Act
        List<BlogPost> result = blogPostRepository.searchByTitleOrSlug("test-post");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test-post", result.get(0).getSlug());
    }

    @Test
    void testSearchByTitleOrSlug_NoMatch_ReturnsEmptyList() {
        // Arrange
        blogPostRepository.save(testPost);

        // Act
        List<BlogPost> result = blogPostRepository.searchByTitleOrSlug("nonexistent");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSave_ValidPost_PersistsToDatabase() {
        // Act
        BlogPost saved = blogPostRepository.save(testPost);

        // Assert
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Test Post", saved.getTitle());
    }

    @Test
    void testDelete_ExistingPost_RemovesFromDatabase() {
        // Arrange
        BlogPost saved = blogPostRepository.save(testPost);
        Long id = saved.getId();

        // Act
        blogPostRepository.deleteById(id);

        // Assert
        Optional<BlogPost> result = blogPostRepository.findById(id);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSave_WithTags_PersistsTagsToDatabase() {
        // Arrange
        testPost.setTags(Arrays.asList("java", "spring-boot", "testing"));

        // Act
        BlogPost saved = blogPostRepository.save(testPost);
        BlogPost retrieved = blogPostRepository.findById(saved.getId()).orElseThrow();

        // Assert
        assertNotNull(retrieved.getTags());
        assertEquals(3, retrieved.getTags().size());
        assertTrue(retrieved.getTags().contains("java"));
        assertTrue(retrieved.getTags().contains("spring-boot"));
        assertTrue(retrieved.getTags().contains("testing"));
    }

    @Test
    void testDelete_WithTags_CascadesDeleteToTags() {
        // Arrange
        testPost.setTags(Arrays.asList("java", "spring-boot"));
        BlogPost saved = blogPostRepository.save(testPost);
        Long id = saved.getId();

        // Act
        blogPostRepository.deleteById(id);

        // Assert
        Optional<BlogPost> result = blogPostRepository.findById(id);
        assertTrue(result.isEmpty());
        // Tags should be cascade deleted automatically
    }
}
