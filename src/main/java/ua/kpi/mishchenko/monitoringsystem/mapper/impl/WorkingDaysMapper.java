package ua.kpi.mishchenko.monitoringsystem.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.mishchenko.monitoringsystem.dto.WorkingDaysByYear;
import ua.kpi.mishchenko.monitoringsystem.dto.WorkingDaysDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.SectionEntity;
import ua.kpi.mishchenko.monitoringsystem.entity.WorkingDaysEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.Mapper;
import ua.kpi.mishchenko.monitoringsystem.repository.SectionRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class WorkingDaysMapper implements Mapper<WorkingDaysEntity, WorkingDaysDTO> {

    private final SectionRepository sectionRepository;

    @Override
    public WorkingDaysEntity dtoToEntity(WorkingDaysDTO dto) {
        if (isNull(dto)) {
            return null;
        }
        WorkingDaysEntity entity = new WorkingDaysEntity();
        entity.setId(dto.getId());
        entity.setSection(sectionRepository.findById(dto.getSectionId()).get());
        entity.setYear(dto.getYear());
        entity.setMonth(dto.getMonth());
        entity.setAmount(dto.getAmount());
        return entity;
    }

    public List<WorkingDaysEntity> yearToEntities(WorkingDaysByYear year) {
        if (isNull(year)) {
            return new ArrayList<>();
        }
        List<WorkingDaysEntity> entities = new ArrayList<>();
        SectionEntity section = sectionRepository.findById(year.getSectionId()).get();
        entities.add(monthToEntity(section, year.getYear(), 1, year.getJanuary()));
        entities.add(monthToEntity(section, year.getYear(), 2, year.getFebruary()));
        entities.add(monthToEntity(section, year.getYear(), 3, year.getMarch()));
        entities.add(monthToEntity(section, year.getYear(), 4, year.getApril()));
        entities.add(monthToEntity(section, year.getYear(), 5, year.getMay()));
        entities.add(monthToEntity(section, year.getYear(), 6, year.getJune()));
        entities.add(monthToEntity(section, year.getYear(), 7, year.getJuly()));
        entities.add(monthToEntity(section, year.getYear(), 8, year.getAugust()));
        entities.add(monthToEntity(section, year.getYear(), 9, year.getSeptember()));
        entities.add(monthToEntity(section, year.getYear(), 10, year.getOctober()));
        entities.add(monthToEntity(section, year.getYear(), 11, year.getNovember()));
        entities.add(monthToEntity(section, year.getYear(), 12, year.getDecember()));
        return entities;
    }

    private WorkingDaysEntity monthToEntity(SectionEntity section, Integer year, Integer month, Integer amount) {
        WorkingDaysEntity entity = new WorkingDaysEntity();
        entity.setYear(year);
        entity.setMonth(month);
        entity.setAmount(amount);
        entity.setSection(section);
        return entity;
    }

    @Override
    public WorkingDaysDTO entityToDto(WorkingDaysEntity entity) {
        if (isNull(entity)) {
            return null;
        }
        WorkingDaysDTO dto = new WorkingDaysDTO();
        dto.setId(entity.getId());
        dto.setSectionId(entity.getSection().getId());
        dto.setYear(entity.getYear());
        dto.setMonth(entity.getMonth());
        dto.setAmount(entity.getAmount());
        return dto;
    }

    @Override
    public List<WorkingDaysEntity> dtosToEntities(List<WorkingDaysDTO> dtos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<WorkingDaysDTO> entitiesToDtos(List<WorkingDaysEntity> entities) {
        if (isNull(entities) || entities.isEmpty()) {
            return new ArrayList<>();
        }
        List<WorkingDaysDTO> dtos = new ArrayList<>();
        for (WorkingDaysEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
}
