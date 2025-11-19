-- ============================================================================
-- Database Initialization Script
-- Description: Create database and user for development
-- ============================================================================

-- Database is created by docker-compose environment variable
-- Skip database creation to avoid duplicate errors

-- Create application user (idempotent)
DO
$$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'petedillo') THEN
    CREATE USER petedillo WITH PASSWORD 'dev_password';
  END IF;
END
$$;

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