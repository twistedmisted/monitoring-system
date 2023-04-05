CREATE TABLE activities
(
    id               BIGINT GENERATED ALWAYS AS IDENTITY,
    name             VARCHAR(255) NOT NULL,
    description      TEXT         NOT NULL,
    money            FLOAT        NOT NULL,
    financial_source TEXT         NOT NULL,
    parameter_id     BIGINT,
    section_id          BIGINT
);

ALTER TABLE activities
    ADD CONSTRAINT pk_activities PRIMARY KEY (id);
ALTER TABLE activities
    ADD CONSTRAINT fk_activities_parameters FOREIGN KEY (parameter_id) REFERENCES parameters (id);
ALTER TABLE activities
    ADD CONSTRAINT fk_activities_sections FOREIGN KEY (section_id) REFERENCES sections (id);
