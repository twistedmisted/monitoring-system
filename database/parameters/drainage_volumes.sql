CREATE TABLE drainage_volumes
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE drainage_volumes
    ADD CONSTRAINT pk_drainage_volumes PRIMARY KEY (id);
ALTER TABLE drainage_volumes
    ADD CONSTRAINT fk_drainage_volumes_sections FOREIGN KEY (section_id) REFERENCES sections (id);
