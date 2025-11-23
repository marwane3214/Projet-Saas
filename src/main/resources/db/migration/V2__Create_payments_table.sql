CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id BIGINT NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    method VARCHAR(50) NOT NULL,
    gateway_reference VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    failure_reason VARCHAR(500),
    version BIGINT DEFAULT 0
);

CREATE INDEX idx_payment_invoice ON payments(invoice_id);
CREATE INDEX idx_payment_status ON payments(status);
CREATE INDEX idx_payment_gateway_ref ON payments(gateway_reference);
