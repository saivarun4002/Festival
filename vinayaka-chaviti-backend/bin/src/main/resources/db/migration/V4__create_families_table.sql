-- US-COMMUNITY: Participating families directory.
CREATE TABLE families (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    family_name VARCHAR(150) NOT NULL,
    representative_name VARCHAR(150),
    published BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_families_published ON families (published);
