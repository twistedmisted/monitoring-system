package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.EnterpriseDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.SetParametersRequest;
import ua.kpi.mishchenko.monitoringsystem.dto.UnitDTO;

import java.util.List;

public interface UnitService {

    UnitDTO getUnitById(Long unitId);

    UnitDTO createEnterprise(UnitDTO unitDTO);

    UnitDTO createDepartment(UnitDTO unitDTO);

    boolean isEnterpriseById(Long enterpriseId);

    List<EnterpriseDTO> getAllEnterprises();

    void setDepartmentParameters(Long departmentId, SetParametersRequest setParametersRequest);
}
