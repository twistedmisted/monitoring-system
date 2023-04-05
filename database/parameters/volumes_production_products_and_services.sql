CREATE TABLE volumes_production_products_and_services
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY,
    section_id BIGINT,
    year    INT NOT NULL,
    month   INT NOT NULL,
    value   FLOAT
);

ALTER TABLE volumes_production_products_and_services
    ADD CONSTRAINT pk_volumes_production_products_and_services PRIMARY KEY (id);
ALTER TABLE volumes_production_products_and_services
    ADD CONSTRAINT fk_volumes_production_products_and_services_sections FOREIGN KEY (section_id) REFERENCES sections (id);
