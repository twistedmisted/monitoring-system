CREATE TABLE gasoline_expenses
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE gasoline_expenses
    ADD CONSTRAINT pk_gasoline_expenses PRIMARY KEY (id);
ALTER TABLE gasoline_expenses
    ADD CONSTRAINT fk_gasoline_expenses_sections FOREIGN KEY (section_id) REFERENCES sections (id);
