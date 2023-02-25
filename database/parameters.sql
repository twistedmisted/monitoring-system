CREATE TABLE parameters
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(255) NOT NULL,
    bean_name  VARCHAR(255) NOT NULL,
    has_tariff BOOLEAN      NOT NULL
);

ALTER TABLE parameters
    ADD CONSTRAINT pk_parameters PRIMARY KEY (id);

-- INSERT INTO parameters (name, bean_name, has_tariff)
-- VALUES ('Обсяги споживання електроенергії (кВт·год)', 'volumes_electricity_consumption'),
--        ('Витрати на електроенергію (грн.)', 'electricity_costs'),
--        ('Обсяги виробництва електроенергії (кВт·год)', 'volumes_electricity_production'),
--        ('Витрати на виробництво електроенергію (грн.)', 'costs_electricity_production'),
--        ('Обсяги виробництва теплової енергії на продаж (Гкал)', 'volumes_thermal_energy_production_sale'),
--        ('Обсяги продажів теплової енергії (грн.)', 'volumes_thermal_energy_sales'),
--        ('Обсяги споживання теплової енергії (Гкал)', 'volumes_thermal_energy_consumption'),
--        ('Витрати на теплову енергію на власні потреби (грн.)', 'heat_energy_costs_own_needs'),
--        ('Обсяги споживання газу (тис. м3)', 'volumes_gas_consumption'),
--        ('Витрати на газ (грн.)', 'gas_expenses'),
--        ('Обсяги споживання води (тис. м3)', 'volumes_water_consumption'),
--        ('Витрати на воду (грн.)', 'water_costs'),
--        ('Обсяги споживання вугілля (тон)', 'volumes_coal_consumption'),
--        ('Витрати на вугілля (грн.)', 'coal_costs'),
--        ('Обсяги споживання мазуту (тон)', 'fuel_oil_consumption_volumes'),
--        ('Витрати на мазут (грн.)', 'costs_fuel_oil'),
--        ('Обсяги споживання дизельного палива (тон)', 'volumes_diesel_fuel_consumption'),
--        ('Витрати на дизельне паливо (грн.)', 'diesel_fuel_costs'),
--        ('Обсяги споживання бензину (тон)', 'volumes_gasoline_consumption'),
--        ('Витрати на бензин (грн.)', 'gasoline_expenses'),
--        ('Обсяги споживання мастильних матеріалів (тон)', 'volumes_consumption_lubricants'),
--        ('Витрати на мастильні матеріали (грн.)', 'costs_lubricants'),
--        ('Обсяги водовідведення (тис. м3)', 'drainage_volumes'),
--        ('Витрати на водовідведення (грн.)', 'drainage_costs'),
--        ('Обсяги твердих побутових відходів (тис. м3 / тон)', 'volumes_solid_household_waste'),
--        ('Витрати на утилізацію твердих побутових відходів (грн.)', 'costs_disposal_solid_household_waste'),
--        ('Температура довкілля (0С)', 'ambient_temperature'),
--        ('Обсяги виробництва продукції та послуг (грн. з ПДВ)', 'volumes_production_products_and_services'),
--        ('Викиди СО', 'CO_emissions');
