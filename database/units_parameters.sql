CREATE TABLE sections_parameters
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id      BIGINT,
    parameter_id BIGINT,
    amount_year  INT NOT NULL DEFAULT 10
);

ALTER TABLE sections_parameters
    ADD CONSTRAINT pk_sections_parameters PRIMARY KEY (id);
ALTER TABLE sections_parameters
    ADD CONSTRAINT fk_sections_parameters_sections FOREIGN KEY (section_id) REFERENCES sections (id);
ALTER TABLE sections_parameters
    ADD CONSTRAINT fk_sections_parameters_parameters FOREIGN KEY (parameter_id) REFERENCES parameters (id);