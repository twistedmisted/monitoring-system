package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.EnterpriseDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.UnitDTO;

import java.util.List;

public interface UnitService {

    UnitDTO createEnterprise(UnitDTO unitDTO);

    UnitDTO createDepartment(UnitDTO unitDTO);

    boolean isEnterpriseById(Long enterpriseId);

    List<EnterpriseDTO> getAllEnterprises();
}
