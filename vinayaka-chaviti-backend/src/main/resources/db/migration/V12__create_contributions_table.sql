-- US-CONTRIBUTIONS: In-kind/special contribution records shown in a
-- searchable, category-tabbed table on the public Donations page.
CREATE TABLE contributions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(20) NOT NULL,
    event_label VARCHAR(100),
    item_name VARCHAR(200) NOT NULL,
    donor_name VARCHAR(150) NOT NULL,
    designation VARCHAR(100),
    quantity VARCHAR(50),
    display_order INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_contributions_category ON contributions (category);
CREATE INDEX idx_contributions_display_order ON contributions (display_order);
