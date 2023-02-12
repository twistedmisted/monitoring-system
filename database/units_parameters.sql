CREATE TABLE units_parameters
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id      BIGINT,
    parameter_id BIGINT,
    amount_year  INT NOT NULL DEFAULT 10
);

ALTER TABLE units_parameters
    ADD CONSTRAINT pk_units_parameters PRIMARY KEY (id);
ALTER TABLE units_parameters
    ADD CONSTRAINT fk_units_parameters_units FOREIGN KEY (unit_id) REFERENCES units (id);
ALTER TABLE units_parameters
    ADD CONSTRAINT fk_units_parameters_parameters FOREIGN KEY (parameter_id) REFERENCES parameters (id);