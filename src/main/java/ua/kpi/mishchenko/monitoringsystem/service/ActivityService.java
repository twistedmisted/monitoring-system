package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.ActivityBO;
import ua.kpi.mishchenko.monitoringsystem.dto.ActivityDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.CreateActivity;

import java.math.BigDecimal;
import java.util.List;

public interface ActivityService {

    List<ActivityDTO> getAllBySectionId(Long sectionId);

    List<ActivityBO> getAllByEnterpriseId(Long enterpriseId);

    void createActivity(CreateActivity createActivity);

    BigDecimal getMoneyByParameterId(Long sectionId, Long parameterId);

    BigDecimal getMoneyByParameterIdForEnterprise(Long enterpriseId, Long parameterId);
}
