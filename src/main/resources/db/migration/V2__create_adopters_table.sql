-- V2__create_adopters_table.sql
CREATE TABLE adopters (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    birth_date DATE,
    address VARCHAR(255),
    phone_number VARCHAR(255)
);