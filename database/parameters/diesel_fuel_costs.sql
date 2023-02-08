CREATE TABLE diesel_fuel_costs
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE diesel_fuel_costs
    ADD CONSTRAINT pk_diesel_fuel_costs PRIMARY KEY (id);
ALTER TABLE diesel_fuel_costs
    ADD CONSTRAINT fk_diesel_fuel_costs_units FOREIGN KEY (unit_id) REFERENCES units (id);
