CREATE TABLE costs_disposal_solid_household_waste
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE costs_disposal_solid_household_waste
    ADD CONSTRAINT pk_costs_disposal_solid_household_waste PRIMARY KEY (id);
ALTER TABLE costs_disposal_solid_household_waste
    ADD CONSTRAINT fk_costs_disposal_solid_household_waste_sections FOREIGN KEY (section_id) REFERENCES sections (id);