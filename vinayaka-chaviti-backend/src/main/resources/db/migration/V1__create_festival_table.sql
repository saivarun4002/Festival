# FILE: V1__create_festival_table.sql

SET NAMES utf8mb4;

CREATE TABLE festivals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    year INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    location VARCHAR(255),
    description TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'UPCOMING',
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    UNIQUE KEY uk_festivals_year (year),
    KEY idx_festivals_status (status),
    KEY idx_festivals_start_date (start_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;