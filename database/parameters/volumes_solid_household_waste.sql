CREATE TABLE volumes_solid_household_waste
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE volumes_solid_household_waste
    ADD CONSTRAINT pk_volumes_solid_household_waste PRIMARY KEY (id);
ALTER TABLE volumes_solid_household_waste
    ADD CONSTRAINT fk_volumes_solid_household_waste_units FOREIGN KEY (unit_id) REFERENCES units (id);
