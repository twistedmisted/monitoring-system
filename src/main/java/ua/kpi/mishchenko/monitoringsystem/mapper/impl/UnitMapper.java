package ua.kpi.mishchenko.monitoringsystem.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.mishchenko.monitoringsystem.dto.UnitDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UnitMapper implements Mapper<UnitEntity, UnitDTO> {

    private final ParameterMapper parameterMapper;

    @Override
    public UnitEntity dtoToEntity(UnitDTO dto) {
        if (dto == null) {
            return null;
        }
        UnitEntity entity = new UnitEntity();
        entity.setId(dto.getId());
        entity.setParentId(dto.getParentId());
        entity.setName(dto.getName());
        entity.setParameters(parameterMapper.dtosToEntities(dto.getParameters()));
        return entity;
    }

    @Override
    public UnitDTO entityToDto(UnitEntity entity) {
        if (entity == null) {
            return null;
        }
        UnitDTO dto = new UnitDTO();
        dto.setId(entity.getId());
        dto.setParentId(entity.getParentId());
        dto.setName(entity.getName());
        dto.setParameters(parameterMapper.entitiesToDtos(entity.getParameters()));
        return dto;
    }

    @Override
    public List<UnitEntity> dtosToEntities(List<UnitDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }
        List<UnitEntity> entities = new ArrayList<>();
        for (UnitDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }

    @Override
    public List<UnitDTO> entitiesToDtos(List<UnitEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        List<UnitDTO> dtos = new ArrayList<>();
        for (UnitEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
}
