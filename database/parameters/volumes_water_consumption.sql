CREATE TABLE volumes_water_consumption
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE volumes_water_consumption
    ADD CONSTRAINT pk_volumes_water_consumption PRIMARY KEY (id);
ALTER TABLE volumes_water_consumption
    ADD CONSTRAINT fk_volumes_water_consumption_units FOREIGN KEY (unit_id) REFERENCES units (id);
