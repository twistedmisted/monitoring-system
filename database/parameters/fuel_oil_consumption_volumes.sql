CREATE TABLE fuel_oil_consumption_volumes
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE fuel_oil_consumption_volumes
    ADD CONSTRAINT pk_fuel_oil_consumption_volumes PRIMARY KEY (id);
ALTER TABLE fuel_oil_consumption_volumes
    ADD CONSTRAINT fk_fuel_oil_consumption_volumes_units FOREIGN KEY (unit_id) REFERENCES units (id);
