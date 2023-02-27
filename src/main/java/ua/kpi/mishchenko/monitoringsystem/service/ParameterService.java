package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.ParameterDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterWithTariff;

import java.util.List;

public interface ParameterService {

    ParameterDTO getParameterByBeanName(String name);

    ParameterWithTariff getParameterWithTariffByName(String name);

    List<ParameterWithTariff> getAllParametersWithTariff();
}
