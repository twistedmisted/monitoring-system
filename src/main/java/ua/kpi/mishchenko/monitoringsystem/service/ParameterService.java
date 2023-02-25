package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.ParameterWithTariff;

import java.util.List;

public interface ParameterService {

    ParameterWithTariff getParameterWithTariffByName(String name);

    List<ParameterWithTariff> getAllParametersWithTariff();
}
