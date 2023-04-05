package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.InputDataDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterYearsInfo;
import ua.kpi.mishchenko.monitoringsystem.dto.TableData;
import ua.kpi.mishchenko.monitoringsystem.dto.YearInfo;

import java.util.List;

public interface ParameterBaseService {

    InputDataDTO getDataByParameterName(Long sectionId, String parameterName);

    TableData getDataByParameterNameWithWorkingDays(Long sectionId, String parameterName);

    YearInfo getDataByParameterNameWithWorkingDaysByYear(Long sectionId, String parameterName, Integer year);

    TableData getCostsDataByParameterNameWithWorkingDays(Long sectionId, String parameterName);

    ParameterYearsInfo getDataForEnterpriseByParameterName(Long sectionId, String parameterName);

    ParameterYearsInfo getCostsDataForEnterpriseByParameterName(Long sectionId, String parameterName);

    List<YearInfo> getYearCostsDataForEnterpriseByParameterName(Long sectionId, String parameterName, Integer year);

    TableData getDataForEnterpriseByParameterNameAndYear(Long enterpriseId, String beanName, Integer year);

    TableData getCostsDataForEnterpriseByParameterNameAndYear(Long enterpriseId, String beanName, Integer year);

    void saveData(Long sectionId, InputDataDTO tableData);
}
