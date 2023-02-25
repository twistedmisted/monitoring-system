package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.ParameterWithTariff;

public interface TariffService {

    void saveTariff(String name, ParameterWithTariff parameterWithTariff);
}
