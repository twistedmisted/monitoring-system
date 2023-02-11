package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

@Data
public class SetParametersRequest {

    private boolean volumesElectricityConsumption;
    private boolean electricityCosts;
    private boolean volumesElectricityProduction;
    private boolean costsElectricityProduction;
    private boolean volumesThermalEnergyProductionSale;
    private boolean volumesThermalEnergySales;
    private boolean volumesThermalEnergyConsumption;
    private boolean heatEnergyCostsOwnNeeds;
    private boolean volumesGasConsumption;
    private boolean gasExpenses;
    private boolean volumesWaterConsumption;
    private boolean waterCosts;
    private boolean volumesCoalConsumption;
    private boolean coalCosts;
    private boolean fuelOilConsumptionVolumes;
    private boolean costsFuelOil;
    private boolean volumesDieselFuelConsumption;
    private boolean dieselFuelCosts;
    private boolean volumesGasolineConsumption;
    private boolean gasolineExpenses;
    private boolean volumesConsumptionLubricants;
    private boolean costsLubricants;
    private boolean drainageVolumes;
    private boolean drainageCosts;
    private boolean volumesSolidHouseholdWaste;
    private boolean costsDisposalSolidHouseholdWaste;
    private boolean ambientTemperature;
    private boolean volumesProductionProductsAndServices;
    private boolean COEmissions;
}
