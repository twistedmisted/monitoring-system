package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.ParameterDTO;

import java.util.List;

public interface UnitParameterService {

    List<String> getAllBeanNameParametersByUnitId(Long unitId);

    List<ParameterDTO> getAllParametersByUnitId(Long unitId);

    void addYear(Long unitId, String parameterName);

    void removeYear(Long unitId, String parameterName, Integer year);
}
