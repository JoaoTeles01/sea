-- V3__create_adoptions_table.sql
CREATE TABLE adoptions (
    id SERIAL PRIMARY KEY,
    animal_id INTEGER REFERENCES animals(id),
    adopter_id INTEGER REFERENCES adopters(id),
    adoption_date DATE,
    devolution_date DATE
);