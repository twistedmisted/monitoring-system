package ua.kpi.mishchenko.monitoringsystem.service;

import java.util.List;

public interface UnitParameterService {

    List<String> getAllBeanNameParametersByUnitId(Long unitId);
}
