-- US-PUJA: Puja schedule and spiritual content tables (Phase 6).
CREATE TABLE puja_schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    puja_type VARCHAR(30) NOT NULL,
    scheduled_date DATE NOT NULL,
    scheduled_time TIME NOT NULL,
    duration_minutes INT,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE spiritual_contents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    category VARCHAR(30) NOT NULL,
    content LONGTEXT NOT NULL,
    display_order INT NOT NULL DEFAULT 0,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_puja_schedules_published ON puja_schedules (published);
CREATE INDEX idx_puja_schedules_date ON puja_schedules (scheduled_date);
CREATE INDEX idx_spiritual_contents_published ON spiritual_contents (published);
CREATE INDEX idx_spiritual_contents_category ON spiritual_contents (category);
