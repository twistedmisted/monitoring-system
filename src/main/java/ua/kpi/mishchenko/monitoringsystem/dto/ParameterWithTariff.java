package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

@Data
public class ParameterWithTariff {

    private ParameterDTO parameter;
    private TariffDTO tariff;
}
