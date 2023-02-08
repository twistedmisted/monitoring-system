CREATE TABLE electricity_costs
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE electricity_costs
    ADD CONSTRAINT pk_electricity_costs PRIMARY KEY (id);
ALTER TABLE electricity_costs
    ADD CONSTRAINT fk_electricity_costs_units FOREIGN KEY (unit_id) REFERENCES units (id);
