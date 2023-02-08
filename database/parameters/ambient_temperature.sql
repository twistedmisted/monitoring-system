CREATE TABLE ambient_temperature
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    unit_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE ambient_temperature
    ADD CONSTRAINT pk_ambient_temperature PRIMARY KEY (id);
ALTER TABLE ambient_temperature
    ADD CONSTRAINT fk_ambient_temperature_units FOREIGN KEY (unit_id) REFERENCES units (id);
