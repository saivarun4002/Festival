-- US-EVENTS: Festival events/schedule table.
CREATE TABLE events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    festival_id BIGINT NOT NULL,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    category VARCHAR(20) NOT NULL,
    event_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME,
    location VARCHAR(255),
    image_url VARCHAR(500),
    published BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT fk_events_festival FOREIGN KEY (festival_id) REFERENCES festivals (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_events_festival_id ON events (festival_id);
CREATE INDEX idx_events_date ON events (event_date);
CREATE INDEX idx_events_category ON events (category);
CREATE INDEX idx_events_published ON events (published);
