CREATE TABLE gas_expenses
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE gas_expenses
    ADD CONSTRAINT pk_gas_expenses PRIMARY KEY (id);
ALTER TABLE gas_expenses
    ADD CONSTRAINT fk_gas_expenses_units FOREIGN KEY (unit_id) REFERENCES units (id);
