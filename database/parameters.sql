CREATE TABLE parameters
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL
);

ALTER TABLE parameters
    ADD CONSTRAINT pk_parameters PRIMARY KEY (id);

INSERT INTO parameters (name)
VALUES ('volumes_electricity_consumption'),
       ('electricity_costs'),
       ('volumes_electricity_production'),
       ('costs_electricity_production'),
       ('volumes_thermal_energy_production_sale'),
       ('volumes_thermal_energy_sales'),
       ('volumes_thermal_energy_consumption'),
       ('heat_energy_costs_own_needs'),
       ('volumes_gas_consumption'),
       ('gas_expenses'),
       ('volumes_water_consumption'),
       ('water_costs'),
       ('volumes_coal_consumption'),
       ('coal_costs'),
       ('fuel_oil_consumption_volumes'),
       ('costs_fuel_oil'),
       ('volumes_diesel_fuel_consumption'),
       ('diesel_fuel_costs'),
       ('volumes_gasoline_consumption'),
       ('gasoline_expenses'),
       ('volumes_consumption_lubricants'),
       ('costs_lubricants'),
       ('drainage_volumes'),
       ('drainage_costs'),
       ('volumes_solid_household_waste'),
       ('costs_disposal_solid_household_waste'),
       ('ambient_temperature'),
       ('volumes_production_products_and_services'),
       ('CO_emissions');
