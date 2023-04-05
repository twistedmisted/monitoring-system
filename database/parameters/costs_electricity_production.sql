CREATE TABLE costs_electricity_production
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE costs_electricity_production
    ADD CONSTRAINT pk_costs_electricity_production PRIMARY KEY (id);
ALTER TABLE costs_electricity_production
    ADD CONSTRAINT fk_costs_electricity_production_sections FOREIGN KEY (section_id) REFERENCES sections (id);
