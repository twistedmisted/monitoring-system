package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.EnterpriseDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.SectionDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.SetParametersRequest;

import java.util.List;

public interface SectionService {

    SectionDTO getSectionById(Long sectionId);

    SectionDTO createEnterprise(SectionDTO sectionDTO);

    SectionDTO createDepartment(SectionDTO sectionDTO);

    boolean isEnterpriseById(Long enterpriseId);

    List<EnterpriseDTO> getAllEnterprises();

    void setDepartmentParameters(Long departmentId, SetParametersRequest setParametersRequest);
}
