CREATE TABLE renewals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subscription_id BIGINT NOT NULL,
    renewal_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMP NULL,
    failure_reason VARCHAR(500),
    retry_count INT,
    version BIGINT DEFAULT 0
);

CREATE INDEX idx_renewal_subscription ON renewals(subscription_id);
CREATE INDEX idx_renewal_status ON renewals(status);
CREATE INDEX idx_renewal_date ON renewals(renewal_date);
