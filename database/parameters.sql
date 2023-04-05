CREATE TABLE parameters
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(255) NOT NULL,
    bean_name  VARCHAR(255) NOT NULL,
    has_tariff BOOLEAN      NOT NULL,
    costs_name VARCHAR(255),
    cons_name  VARCHAR(255)
);

ALTER TABLE parameters
    ADD CONSTRAINT pk_parameters PRIMARY KEY (id);

INSERT INTO parameters (name, bean_name, has_tariff, costs_name, cons_name)
VALUES ('Електроенергія', 'volumes_electricity_consumption', true, 'Витрати на електроенергію (грн.)',
        'Обсяги споживання електроенергії (кВт·год)'),
       ('Електроенергія (продаж)', 'volumes_electricity_production', true,
        'Витрати на виробництво електроенергію (грн.)', 'Обсяги виробництва електроенергії (кВт·год)'),
       ('Електроенергія (виробництво)', 'volumes_thermal_energy_production_sale', true,
        'Обсяги продажів теплової енергії (грн.)', 'Обсяги виробництва теплової енергії на продаж (Гкал)'),
       ('Теплова енергія', 'volumes_thermal_energy_consumption', true,
        'Витрати на теплову енергію на власні потреби (грн.)', 'Обсяги споживання теплової енергії (Гкал)'),
       ('Газ', 'volumes_gas_consumption', true, 'Витрати на газ (грн.)', 'Обсяги споживання газу (тис. м3)'),
       ('Вода', 'volumes_water_consumption', true, 'Витрати на воду (грн.)', 'Обсяги споживання води (тис. м3)'),
       ('Вугілля', 'volumes_coal_consumption', true, 'Витрати на вугілля (грн.)', 'Обсяги споживання вугілля (тон)'),
       ('Мазут', 'fuel_oil_consumption_volumes', true, 'Витрати на мазут (грн.)', 'Обсяги споживання мазуту (тон)'),
       ('Дизельне паливо', 'volumes_diesel_fuel_consumption', true, 'Витрати на дизельне паливо (грн.)',
        'Обсяги споживання дизельного палива (тон)'),
       ('Бензин', 'volumes_gasoline_consumption', true, 'Витрати на бензин (грн.)', 'Обсяги споживання бензину (тон)'),
       ('Мастильні матеріали', 'volumes_consumption_lubricants', true, 'Витрати на мастильні матеріали (грн.)',
        'Обсяги споживання мастильних матеріалів (тон)'),
       ('Водовідведення', 'drainage_volumes', true, 'Витрати на водовідведення (грн.)',
        'Обсяги водовідведення (тис. м3)'),
       ('Тверді побутові відходи', 'volumes_solid_household_waste', true,
        'Витрати на утилізацію твердих побутових відходів (грн.)', 'Обсяги твердих побутових відходів (тис. м3 / тон)'),
       ('Температура довкілля', 'ambient_temperature', false, null, 'Температура довкілля (0С)'),
       ('Обсяги виробництві продукції та послуг', 'volumes_production_products_and_services', false, null,
        'Обсяги виробництва продукції та послуг (грн. з ПДВ)'),
       ('Викиди СО', 'CO_emissions', false, null, 'Викиди СО');
