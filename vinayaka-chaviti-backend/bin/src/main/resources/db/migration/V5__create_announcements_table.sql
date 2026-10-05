-- US-ANNOUNCEMENTS: Festival announcements/notices.
CREATE TABLE announcements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    message TEXT NOT NULL,
    priority VARCHAR(20) NOT NULL DEFAULT 'NORMAL',
    published BOOLEAN NOT NULL DEFAULT FALSE,
    published_at TIMESTAMP(6),
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_announcements_published ON announcements (published);
CREATE INDEX idx_announcements_priority ON announcements (priority);
