package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.InputDataDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.TableData;

public interface ParameterBaseService {

    InputDataDTO getDataByParameterName(Long unitId, String parameterName);

    TableData getDataByParameterNameWithWorkingDays(Long unitId, String parameterName);

    TableData getDataForEnterpriseByParameterName(Long unitId, String parameterName);

    TableData getDataForEnterpriseByParameterNameAndYear(Long enterpriseId, String beanName, Integer year);

    void saveData(Long unitId, InputDataDTO tableData);
}
