CREATE TABLE volumes_thermal_energy_sales
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE volumes_thermal_energy_sales
    ADD CONSTRAINT pk_volumes_thermal_energy_sales PRIMARY KEY (id);
ALTER TABLE volumes_thermal_energy_sales
    ADD CONSTRAINT fk_volumes_thermal_energy_sales_units FOREIGN KEY (unit_id) REFERENCES units (id);
