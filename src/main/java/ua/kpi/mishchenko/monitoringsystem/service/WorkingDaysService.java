package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.WorkingDaysByYear;

public interface WorkingDaysService {

    WorkingDaysByYear getAllWorkingDaysBySectionIdAndYear(Long sectionId, Integer year);

    void saveWorkingDays(WorkingDaysByYear workingDays);
}
