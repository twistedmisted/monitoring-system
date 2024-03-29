CREATE TABLE working_days
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    amount  INT NOT NULL
);

ALTER TABLE working_days
    ADD CONSTRAINT pk_working_days PRIMARY KEY (id);
ALTER TABLE working_days
    ADD CONSTRAINT fk_working_days_units FOREIGN KEY (unit_id) REFERENCES units (id);