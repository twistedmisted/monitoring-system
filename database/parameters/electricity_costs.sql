CREATE TABLE electricity_costs
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE electricity_costs
    ADD CONSTRAINT pk_electricity_costs PRIMARY KEY (id);
ALTER TABLE electricity_costs
    ADD CONSTRAINT fk_electricity_costs_sections FOREIGN KEY (section_id) REFERENCES sections (id);
