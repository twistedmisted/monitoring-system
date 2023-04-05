CREATE TABLE comments
(
    id             BIGINT GENERATED ALWAYS AS IDENTITY,
    text           TEXT,
    section_id        BIGINT,
    parameter_name VARCHAR(255) NOT NULL
);

ALTER TABLE comments
    ADD CONSTRAINT pk_comments PRIMARY KEY (id);
ALTER TABLE comments
    ADD CONSTRAINT fk_comments_sections FOREIGN KEY (section_id) REFERENCES sections (id);