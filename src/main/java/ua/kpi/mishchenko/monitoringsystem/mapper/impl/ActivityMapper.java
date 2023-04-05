package ua.kpi.mishchenko.monitoringsystem.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.mishchenko.monitoringsystem.dto.ActivityDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.CreateActivity;
import ua.kpi.mishchenko.monitoringsystem.entity.ActivityEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.Mapper;
import ua.kpi.mishchenko.monitoringsystem.repository.ParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.repository.SectionRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class ActivityMapper implements Mapper<ActivityEntity, ActivityDTO> {

    private final SectionRepository sectionRepository;
    private final ParameterRepository parameterRepository;
    private final SectionMapper sectionMapper;
    private final ParameterMapper parameterMapper;

    @Override
    public ActivityEntity dtoToEntity(ActivityDTO dto) {
        if (isNull(dto)) {
            return null;
        }
        ActivityEntity entity = new ActivityEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setMoney(dto.getMoney());
        entity.setDescription(dto.getDescription());
        entity.setFinancialSource(dto.getFinancialSource());
        entity.setParameter(parameterRepository.findById(dto.getParameter().getId()).get());
        entity.setSection(sectionRepository.findById(dto.getSection().getId()).get());
        return entity;
    }

    @Override
    public ActivityDTO entityToDto(ActivityEntity entity) {
        if (isNull(entity)) {
            return null;
        }
        ActivityDTO dto = new ActivityDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setMoney(entity.getMoney());
        dto.setDescription(entity.getDescription());
        dto.setFinancialSource(entity.getFinancialSource());
        dto.setParameter(parameterMapper.entityToDto(entity.getParameter()));
        dto.setSection(sectionMapper.entityToDto(entity.getSection()));
        return dto;
    }

    public ActivityEntity createActivityToEntity(CreateActivity dto) {
        if (isNull(dto)) {
            return null;
        }
        ActivityEntity entity = new ActivityEntity();
        entity.setName(dto.getName());
        entity.setMoney(dto.getMoney());
        entity.setDescription(dto.getDescription());
        entity.setFinancialSource(dto.getFinancialSource());
        entity.setParameter(parameterRepository.findById(dto.getParameterId()).get());
        entity.setSection(sectionRepository.findById(dto.getDepartmentId()).get());
        return entity;
    }

    @Override
    public List<ActivityEntity> dtosToEntities(List<ActivityDTO> dtos) {
        if (isNull(dtos) || dtos.isEmpty()) {
            return new ArrayList<>();
        }
        List<ActivityEntity> entities = new ArrayList<>();
        for (ActivityDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }

    @Override
    public List<ActivityDTO> entitiesToDtos(List<ActivityEntity> entities) {
        if (isNull(entities)) {
            return new ArrayList<>();
        }
        List<ActivityDTO> dtos = new ArrayList<>();
        for (ActivityEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
}
