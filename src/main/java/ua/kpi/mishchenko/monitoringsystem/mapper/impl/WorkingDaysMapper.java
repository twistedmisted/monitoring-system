package ua.kpi.mishchenko.monitoringsystem.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.mishchenko.monitoringsystem.dto.WorkingDaysDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.WorkingDaysEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.Mapper;
import ua.kpi.mishchenko.monitoringsystem.repository.UnitRepository;

import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class WorkingDaysMapper implements Mapper<WorkingDaysEntity, WorkingDaysDTO> {

    private final UnitRepository unitRepository;

    @Override
    public WorkingDaysEntity dtoToEntity(WorkingDaysDTO dto) {
        if (isNull(dto)) {
            return null;
        }
        WorkingDaysEntity entity = new WorkingDaysEntity();
        entity.setId(dto.getId());
        entity.setUnit(unitRepository.findById(dto.getUnitId()).get());
        entity.setYear(dto.getYear());
        entity.setJanuary(dto.getJanuary());
        entity.setFebruary(dto.getFebruary());
        entity.setMarch(dto.getMarch());
        entity.setApril(dto.getApril());
        entity.setMay(dto.getMay());
        entity.setJune(dto.getJune());
        entity.setJuly(dto.getJuly());
        entity.setAugust(dto.getAugust());
        entity.setSeptember(dto.getSeptember());
        entity.setOctober(dto.getOctober());
        entity.setNovember(dto.getNovember());
        entity.setDecember(dto.getDecember());
        return entity;
    }

    @Override
    public WorkingDaysDTO entityToDto(WorkingDaysEntity entity) {
        if (isNull(entity)) {
            return null;
        }
        WorkingDaysDTO dto = new WorkingDaysDTO();
        dto.setId(entity.getId());
        dto.setUnitId(entity.getUnit().getId());
        dto.setYear(entity.getYear());
        dto.setJanuary(entity.getJanuary());
        dto.setFebruary(entity.getFebruary());
        dto.setMarch(entity.getMarch());
        dto.setApril(entity.getApril());
        dto.setMay(entity.getMay());
        dto.setJune(entity.getJune());
        dto.setJuly(entity.getJuly());
        dto.setAugust(entity.getAugust());
        dto.setSeptember(entity.getSeptember());
        dto.setOctober(entity.getOctober());
        dto.setNovember(entity.getNovember());
        dto.setDecember(entity.getDecember());
        return dto;
    }

    @Override
    public List<WorkingDaysEntity> dtosToEntities(List<WorkingDaysDTO> dtos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<WorkingDaysDTO> entitiesToDtos(List<WorkingDaysEntity> entities) {
        throw new UnsupportedOperationException();
    }
}
