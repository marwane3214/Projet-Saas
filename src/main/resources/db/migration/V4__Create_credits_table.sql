CREATE TABLE credits (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    invoice_id BIGINT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    reason VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    applied BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

CREATE INDEX idx_credit_customer ON credits(customer_id);
CREATE INDEX idx_credit_invoice ON credits(invoice_id);
