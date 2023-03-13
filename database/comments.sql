CREATE TABLE comments
(
    id             BIGINT GENERATED ALWAYS AS IDENTITY,
    text           TEXT,
    unit_id        BIGINT,
    parameter_name VARCHAR(255) NOT NULL
);

ALTER TABLE comments
    ADD CONSTRAINT pk_comments PRIMARY KEY (id);
ALTER TABLE comments
    ADD CONSTRAINT fk_comments_units FOREIGN KEY (unit_id) REFERENCES units (id);