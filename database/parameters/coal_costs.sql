CREATE TABLE coal_costs
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE coal_costs
    ADD CONSTRAINT pk_coal_costs PRIMARY KEY (id);
ALTER TABLE coal_costs
    ADD CONSTRAINT fk_coal_costs_sections FOREIGN KEY (section_id) REFERENCES sections (id);
