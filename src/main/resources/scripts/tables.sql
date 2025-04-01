CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE requests (
    id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid(),
    brand VARCHAR NOT NULL,
    request_type VARCHAR NOT NULL,
    sent_date DATE NOT NULL,
    contact_name VARCHAR NOT NULL,
    contact_number VARCHAR NOT NULL
);

CREATE TABLE contacts (
    id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    number VARCHAR NOT NULL,
    request_id VARCHAR NOT NULL,
    CONSTRAINT fk_request FOREIGN KEY (request_id) REFERENCES requests(id) ON DELETE CASCADE
);