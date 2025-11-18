-- ============================================================================
-- Database Initialization Script
-- Description: Create database and user for development
-- ============================================================================

-- Create database (run as postgres superuser)
CREATE DATABASE petedillo_blog
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE = template0;

-- Create application user
CREATE USER petedillo WITH PASSWORD 'dev_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE petedillo_blog TO petedillo;

-- Connect to the database
\c petedillo_blog

-- Grant schema privileges
GRANT ALL ON SCHEMA public TO petedillo;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO petedillo;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO petedillo;

-- Set default privileges for future tables
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO petedillo;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO petedillo;