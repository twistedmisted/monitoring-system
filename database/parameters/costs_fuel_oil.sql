CREATE TABLE costs_fuel_oil
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE costs_fuel_oil
    ADD CONSTRAINT pk_costs_fuel_oil PRIMARY KEY (id);
ALTER TABLE costs_fuel_oil
    ADD CONSTRAINT fk_costs_fuel_oil_sections FOREIGN KEY (section_id) REFERENCES sections (id);
