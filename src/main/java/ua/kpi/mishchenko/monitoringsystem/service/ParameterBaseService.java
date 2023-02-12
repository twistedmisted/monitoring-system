package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.InputDTO;

public interface ParameterBaseService {

    InputDTO getDataByParameterName(Long unitId, String parameterName);

    InputDTO getDataForEnterpriseByParameterName(Long unitId, String parameterName);

    InputDTO getDataForEnterpriseByParameterNameAndYear(Long enterpriseId, String beanName, Integer year);

    void saveData(Long unitId, String parameterName, InputDTO tableData);
}
