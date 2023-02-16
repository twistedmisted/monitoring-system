package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.WorkingDaysDTO;
import ua.kpi.mishchenko.monitoringsystem.mapper.impl.WorkingDaysMapper;
import ua.kpi.mishchenko.monitoringsystem.repository.WorkingDaysRepository;
import ua.kpi.mishchenko.monitoringsystem.service.WorkingDaysService;

@Service
@RequiredArgsConstructor
public class WorkingDaysServiceImpl implements WorkingDaysService {

    private final WorkingDaysMapper workingDaysMapper;
    private final WorkingDaysRepository workingDaysRepository;

    @Override
    public WorkingDaysDTO getWorkingDaysByUnitIdAndYear(Long unitId, Integer year) {
        WorkingDaysDTO workingDaysDTO = workingDaysMapper.entityToDto(workingDaysRepository.findByUnitIdAndYear(unitId, year)
                .orElse(null));
        if (workingDaysDTO == null) {
            workingDaysDTO = new WorkingDaysDTO();
            workingDaysDTO.setYear(year);
        }
        return workingDaysDTO;
    }

    @Override
    public void saveWorkingDays(WorkingDaysDTO workingDays) {
        workingDaysRepository.save(workingDaysMapper.dtoToEntity(workingDays));
    }
}
