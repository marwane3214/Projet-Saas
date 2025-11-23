-- MySQL Schema for Customer Service
-- Note: With ddl-auto: update, Hibernate will create tables automatically
-- This script is for reference or manual execution

-- Create companies table
CREATE TABLE IF NOT EXISTS companies (
    id CHAR(36) PRIMARY KEY,
    legal_name VARCHAR(255) NOT NULL,
    vat_number VARCHAR(20) NOT NULL UNIQUE,
    country VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create billing_addresses table
CREATE TABLE IF NOT EXISTS billing_addresses (
    id CHAR(36) PRIMARY KEY,
    line1 VARCHAR(255) NOT NULL,
    line2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL
);

-- Create customers table
CREATE TABLE IF NOT EXISTS customers (
    id CHAR(36) PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    company_id CHAR(36) NOT NULL,
    billing_address_id CHAR(36),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_customer_company FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    CONSTRAINT fk_customer_billing_address FOREIGN KEY (billing_address_id) REFERENCES billing_addresses(id) ON DELETE SET NULL
);

-- Create contacts table
CREATE TABLE IF NOT EXISTS contacts (
    id CHAR(36) PRIMARY KEY,
    customer_id CHAR(36) NOT NULL,
    type ENUM('BILLING', 'TECHNICAL', 'OWNER') NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    CONSTRAINT fk_contact_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_customers_company_id ON customers(company_id);
CREATE INDEX IF NOT EXISTS idx_customers_email ON customers(email);
CREATE INDEX IF NOT EXISTS idx_contacts_customer_id ON contacts(customer_id);
CREATE INDEX IF NOT EXISTS idx_companies_vat_number ON companies(vat_number);
