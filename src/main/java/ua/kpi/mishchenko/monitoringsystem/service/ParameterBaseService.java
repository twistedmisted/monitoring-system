package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.InputDTO;

public interface ParameterBaseService {

    InputDTO getDataByParameterName(Long unitId, String parameterName);

    void saveData(Long unitId, String parameterName, InputDTO tableData);
}
