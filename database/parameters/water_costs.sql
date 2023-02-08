CREATE TABLE water_costs
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE water_costs
    ADD CONSTRAINT pk_water_costs PRIMARY KEY (id);
ALTER TABLE water_costs
    ADD CONSTRAINT fk_water_costs_units FOREIGN KEY (unit_id) REFERENCES units (id);
