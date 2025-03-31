-- V1__create_animals_table.sql
CREATE TABLE animals (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    species VARCHAR(255) NOT NULL,
    breed VARCHAR(255),
    age INTEGER,
    status VARCHAR(20) DEFAULT 'AVAILABLE'
);