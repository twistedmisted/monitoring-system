-- CREATE TABLE working_days
-- (
--     id        BIGINT GENERATED ALWAYS AS IDENTITY,
--     unit_id   BIGINT,
--     year      INT NOT NULL,
--     january   INT NOT NULL,
--     february  INT NOT NULL,
--     march     INT NOT NULL,
--     april     INT NOT NULL,
--     may       INT NOT NULL,
--     june      INT NOT NULL,
--     july      INT NOT NULL,
--     august    INT NOT NULL,
--     september INT NOT NULL,
--     october   INT NOT NULL,
--     november  INT NOT NULL,
--     december  INT NOT NULL
-- );

CREATE TABLE working_days
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   INT NOT NULL
);

ALTER TABLE working_days
    ADD CONSTRAINT pk_working_days PRIMARY KEY (id);
ALTER TABLE working_days
    ADD CONSTRAINT fk_working_days_units FOREIGN KEY (unit_id) REFERENCES units (id);