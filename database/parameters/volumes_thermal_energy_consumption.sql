CREATE TABLE volumes_thermal_energy_consumption
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE volumes_thermal_energy_consumption
    ADD CONSTRAINT pk_volumes_thermal_energy_consumption PRIMARY KEY (id);
ALTER TABLE volumes_thermal_energy_consumption
    ADD CONSTRAINT fk_volumes_thermal_energy_consumption_units FOREIGN KEY (unit_id) REFERENCES units (id);
