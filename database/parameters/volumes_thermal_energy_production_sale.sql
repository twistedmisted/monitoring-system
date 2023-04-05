CREATE TABLE volumes_thermal_energy_production_sale
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE volumes_thermal_energy_production_sale
    ADD CONSTRAINT pk_volumes_thermal_energy_production_sale PRIMARY KEY (id);
ALTER TABLE volumes_thermal_energy_production_sale
    ADD CONSTRAINT fk_volumes_thermal_energy_production_sale_sections FOREIGN KEY (section_id) REFERENCES sections (id);
