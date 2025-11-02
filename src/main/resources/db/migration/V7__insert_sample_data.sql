-- ============================================================================
-- Migration: V7__insert_sample_data.sql
-- Description: Insert sample blog post for development and testing
-- Author: Pete Dillo
-- Date: 2025-10-28
-- Note: This migration should only run in dev environment
-- ============================================================================

-- Insert sample featured blog post
INSERT INTO blog_posts (
    title, 
    slug, 
    content, 
    excerpt, 
    status, 
    is_featured,
    published_at
) VALUES (
    'Welcome to PeteDillo.com',
    'welcome-to-petedillo-com',
    E'# Welcome to My Blog\n\nThis is the first post on my new blog platform. I built this site using:\n\n- **Frontend**: React with Tailwind CSS\n- **Backend**: Spring Boot 3\n- **Database**: PostgreSQL\n- **Infrastructure**: Kubernetes on Proxmox\n\n## What to Expect\n\nI''ll be sharing my journey building this platform, setting up my homelab, and exploring various technologies.\n\nStay tuned for more content!',
    'Welcome to my new blog! Learn about the tech stack and what''s coming next.',
    'PUBLISHED',
    TRUE,
    CURRENT_TIMESTAMP
);

-- Get the ID of the inserted post
DO $$
DECLARE
    post_id BIGINT;
BEGIN
    SELECT id INTO post_id FROM blog_posts WHERE slug = 'welcome-to-petedillo-com';
    
    -- Insert sample tags
    INSERT INTO blog_tags (blog_post_id, tag_name) VALUES
        (post_id, 'welcome'),
        (post_id, 'meta'),
        (post_id, 'tech-stack');
END $$
;

-- Insert another sample post (draft)
INSERT INTO blog_posts (
    title, 
    slug, 
    content, 
    excerpt, 
    status, 
    is_featured
) VALUES (
    'Building My Proxmox Homelab',
    'building-my-proxmox-homelab',
    E'# My Homelab Journey\n\n*This post is still being written...*\n\n## Hardware\n\n- CPU: TBD\n- RAM: TBD\n- Storage: 1TB HDD\n\n## Software\n\n- Proxmox VE\n- Kubernetes\n- ArgoCD',
    'A deep dive into my homelab setup with Proxmox, Kubernetes, and more.',
    'DRAFT',
    FALSE
);

-- Get the draft post ID and add tags
DO $$
DECLARE
    post_id BIGINT;
BEGIN
    SELECT id INTO post_id FROM blog_posts WHERE slug = 'building-my-proxmox-homelab';
    
    INSERT INTO blog_tags (blog_post_id, tag_name) VALUES
        (post_id, 'homelab'),
        (post_id, 'proxmox'),
        (post_id, 'infrastructure');
END $$
;
