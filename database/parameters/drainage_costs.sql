CREATE TABLE drainage_costs
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE drainage_costs
    ADD CONSTRAINT pk_drainage_costs PRIMARY KEY (id);
ALTER TABLE drainage_costs
    ADD CONSTRAINT fk_drainage_costs_sections FOREIGN KEY (section_id) REFERENCES sections (id);
