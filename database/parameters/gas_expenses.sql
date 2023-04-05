CREATE TABLE gas_expenses
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE gas_expenses
    ADD CONSTRAINT pk_gas_expenses PRIMARY KEY (id);
ALTER TABLE gas_expenses
    ADD CONSTRAINT fk_gas_expenses_sections FOREIGN KEY (section_id) REFERENCES sections (id);
