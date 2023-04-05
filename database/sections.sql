CREATE TABLE sections
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY,
    parent_id BIGINT NOT NULL DEFAULT 0,
    name      VARCHAR(255)
);

ALTER TABLE sections
    ADD CONSTRAINT pk_sections PRIMARY KEY (id);