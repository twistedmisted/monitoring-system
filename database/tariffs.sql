CREATE TABLE tariffs
(
    parameter_id BIGINT,
    price        FLOAT NOT NULL
);

ALTER TABLE tariffs
    ADD CONSTRAINT pk_tariffs PRIMARY KEY (parameter_id);
ALTER TABLE tariffs
    ADD CONSTRAINT fk_tariffs_parameters FOREIGN KEY (parameter_id) REFERENCES parameters (id);