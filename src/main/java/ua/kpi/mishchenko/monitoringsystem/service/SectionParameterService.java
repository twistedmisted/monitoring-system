package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.ParameterDTO;

import java.util.List;

public interface SectionParameterService {

    List<String> getAllBeanNameParametersBySectionId(Long sectionId);

    List<ParameterDTO> getAllParametersBySectionId(Long sectionId);

    void addYear(Long sectionId, String parameterName);

    void removeYear(Long sectionId, String parameterName, Integer year);

    List<ParameterDTO> getAllParametersByEnterpriseId(Long enterpriseId);
}
