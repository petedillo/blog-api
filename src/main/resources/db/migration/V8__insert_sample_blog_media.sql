-- V8: Insert sample blog media for existing posts
-- Uses Unsplash placeholder images

-- Cover images (display_order = 0)
INSERT INTO blog_media (blog_post_id, media_type, external_url, display_order, alt_text, caption, created_at)
SELECT 
    id,
    'EXTERNAL_IMAGE',
    'https://images.unsplash.com/photo-1499750310107-5fef28a66643?w=1200&h=600&fit=crop',
    0,
    'Blog writing workspace with laptop and coffee',
    'Cover image for ' || title,
    NOW()
FROM blog_posts
WHERE slug = 'sprint-1-infrastructure-foundation';

INSERT INTO blog_media (blog_post_id, media_type, external_url, display_order, alt_text, caption, created_at)
SELECT 
    id,
    'EXTERNAL_IMAGE',
    'https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=1200&h=600&fit=crop',
    0,
    'Server infrastructure and networking cables',
    'Cover image for ' || title,
    NOW()
FROM blog_posts
WHERE slug = 'phase-0-planning-infrastructure';

-- Additional media items (display_order > 0)
INSERT INTO blog_media (blog_post_id, media_type, external_url, display_order, alt_text, caption, created_at)
SELECT 
    id,
    'EXTERNAL_IMAGE',
    'https://images.unsplash.com/photo-1667372393119-3d4c48d07fc9?w=800&h=600&fit=crop',
    1,
    'Kubernetes cluster architecture diagram',
    'Kubernetes deployment overview',
    NOW()
FROM blog_posts
WHERE slug = 'sprint-1-infrastructure-foundation';
