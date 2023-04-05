CREATE TABLE diesel_fuel_costs
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE diesel_fuel_costs
    ADD CONSTRAINT pk_diesel_fuel_costs PRIMARY KEY (id);
ALTER TABLE diesel_fuel_costs
    ADD CONSTRAINT fk_diesel_fuel_costs_sections FOREIGN KEY (section_id) REFERENCES sections (id);
