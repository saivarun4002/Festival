-- US-DONATIONS: Donations/payment table (Phase 7). Mock payment gateway flow
-- in dev: created as PENDING with a generated payment_reference, then an
-- admin-confirmed PATCH /api/donations/{id}/confirm marks it COMPLETED.
CREATE TABLE donations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    donor_name VARCHAR(150),
    email VARCHAR(150),
    phone VARCHAR(20),
    amount DECIMAL(12,2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'INR',
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    payment_reference VARCHAR(100),
    message TEXT,
    anonymous BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_donations_status ON donations (status);
CREATE INDEX idx_donations_created_at ON donations (created_at);
