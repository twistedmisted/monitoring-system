package ua.kpi.mishchenko.monitoringsystem.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.mishchenko.monitoringsystem.dto.SectionDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.SectionEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SectionMapper implements Mapper<SectionEntity, SectionDTO> {

    private final ParameterMapper parameterMapper;

    @Override
    public SectionEntity dtoToEntity(SectionDTO dto) {
        if (dto == null) {
            return null;
        }
        SectionEntity entity = new SectionEntity();
        entity.setId(dto.getId());
        entity.setParentId(dto.getParentId());
        entity.setName(dto.getName());
//        entity.setParameters(parameterMapper.dtosToEntities(dto.getParameters()));
        return entity;
    }

    @Override
    public SectionDTO entityToDto(SectionEntity entity) {
        if (entity == null) {
            return null;
        }
        SectionDTO dto = new SectionDTO();
        dto.setId(entity.getId());
        dto.setParentId(entity.getParentId());
        dto.setName(entity.getName());
//        dto.setParameters(parameterMapper.entitiesToDtos(entity.getParameters()));
        return dto;
    }

    @Override
    public List<SectionEntity> dtosToEntities(List<SectionDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }
        List<SectionEntity> entities = new ArrayList<>();
        for (SectionDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }

    @Override
    public List<SectionDTO> entitiesToDtos(List<SectionEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        List<SectionDTO> dtos = new ArrayList<>();
        for (SectionEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
}
