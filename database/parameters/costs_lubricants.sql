CREATE TABLE costs_lubricants
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE costs_lubricants
    ADD CONSTRAINT pk_costs_lubricants PRIMARY KEY (id);
ALTER TABLE costs_lubricants
    ADD CONSTRAINT fk_costs_lubricants_sections FOREIGN KEY (section_id) REFERENCES sections (id);
