package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.WorkingDaysDTO;

public interface WorkingDaysService {

    WorkingDaysDTO getWorkingDaysByUnitIdAndYear(Long unitId, Integer year);

    void saveWorkingDays(WorkingDaysDTO workingDays);
}
