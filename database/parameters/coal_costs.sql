CREATE TABLE coal_costs
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE coal_costs
    ADD CONSTRAINT pk_coal_costs PRIMARY KEY (id);
ALTER TABLE coal_costs
    ADD CONSTRAINT fk_coal_costs_units FOREIGN KEY (unit_id) REFERENCES units (id);
