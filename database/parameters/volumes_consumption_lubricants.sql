CREATE TABLE volumes_consumption_lubricants
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE volumes_consumption_lubricants
    ADD CONSTRAINT pk_volumes_consumption_lubricants PRIMARY KEY (id);
ALTER TABLE volumes_consumption_lubricants
    ADD CONSTRAINT fk_volumes_consumption_lubricants_units FOREIGN KEY (unit_id) REFERENCES units (id);
