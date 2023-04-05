CREATE TABLE CO_emissions
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE CO_emissions
    ADD CONSTRAINT pk_CO_emissions PRIMARY KEY (id);
ALTER TABLE CO_emissions
    ADD CONSTRAINT fk_CO_emissions_sections FOREIGN KEY (section_id) REFERENCES sections (id);
