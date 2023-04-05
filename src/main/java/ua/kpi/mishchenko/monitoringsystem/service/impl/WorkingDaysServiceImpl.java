package ua.kpi.mishchenko.monitoringsystem.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.WorkingDaysByYear;
import ua.kpi.mishchenko.monitoringsystem.dto.WorkingDaysDTO;
import ua.kpi.mishchenko.monitoringsystem.mapper.impl.WorkingDaysMapper;
import ua.kpi.mishchenko.monitoringsystem.repository.WorkingDaysRepository;
import ua.kpi.mishchenko.monitoringsystem.service.WorkingDaysService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkingDaysServiceImpl implements WorkingDaysService {

    private final WorkingDaysMapper workingDaysMapper;
    private final WorkingDaysRepository workingDaysRepository;

    @Override
    public WorkingDaysByYear getAllWorkingDaysBySectionIdAndYear(Long sectionId, Integer year) {
        List<WorkingDaysDTO> workingDays = workingDaysMapper.entitiesToDtos(workingDaysRepository.findAllBySectionIdAndYearOrderByYearAscMonthAsc(sectionId, year));
        if (workingDays.isEmpty()) {
            return new WorkingDaysByYear(year);
        }
        WorkingDaysByYear workingDaysByYear = new WorkingDaysByYear();
        workingDaysByYear.setYear(workingDays.get(0).getYear());
        workingDaysByYear.setSectionId(workingDays.get(0).getSectionId());
        for (WorkingDaysDTO wd : workingDays) {
            switch (wd.getMonth()) {
                case 1 -> workingDaysByYear.setJanuary(wd.getAmount());
                case 2 -> workingDaysByYear.setFebruary(wd.getAmount());
                case 3 -> workingDaysByYear.setMarch(wd.getAmount());
                case 4 -> workingDaysByYear.setApril(wd.getAmount());
                case 5 -> workingDaysByYear.setMay(wd.getAmount());
                case 6 -> workingDaysByYear.setJune(wd.getAmount());
                case 7 -> workingDaysByYear.setJuly(wd.getAmount());
                case 8 -> workingDaysByYear.setAugust(wd.getAmount());
                case 9 -> workingDaysByYear.setSeptember(wd.getAmount());
                case 10 -> workingDaysByYear.setOctober(wd.getAmount());
                case 11 -> workingDaysByYear.setNovember(wd.getAmount());
                case 12 -> workingDaysByYear.setDecember(wd.getAmount());
            }
        }
        return workingDaysByYear;
    }

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void saveWorkingDays(WorkingDaysByYear workingDaysByYear) {
        jdbcTemplate.update("DELETE FROM working_days WHERE section_id = ? and year = ?",
                workingDaysByYear.getSectionId(), workingDaysByYear.getYear());
        workingDaysRepository.saveAll(workingDaysMapper.yearToEntities(workingDaysByYear));
    }
}
