package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.UnitDTO;

public interface UnitService {

    UnitDTO createEnterprise(UnitDTO unitDTO);

    UnitDTO createDepartment(UnitDTO unitDTO);
}
