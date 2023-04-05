CREATE TABLE heat_energy_costs_own_needs
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE heat_energy_costs_own_needs
    ADD CONSTRAINT pk_heat_energy_costs_own_needs PRIMARY KEY (id);
ALTER TABLE heat_energy_costs_own_needs
    ADD CONSTRAINT fk_heat_energy_costs_own_needs_sections FOREIGN KEY (section_id) REFERENCES sections (id);
