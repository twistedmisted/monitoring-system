package ua.kpi.mishchenko.monitoringsystem.mapper.impl;

import org.springframework.stereotype.Component;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.ParameterEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParameterMapper implements Mapper<ParameterEntity, ParameterDTO> {

    @Override
    public ParameterEntity dtoToEntity(ParameterDTO dto) {
        if (dto == null) {
            return null;
        }
        ParameterEntity entity = new ParameterEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    @Override
    public ParameterDTO entityToDto(ParameterEntity entity) {
        if (entity == null) {
            return null;
        }
        ParameterDTO dto = new ParameterDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public List<ParameterEntity> dtosToEntities(List<ParameterDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }
        List<ParameterEntity> entities = new ArrayList<>();
        for (ParameterDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }

    @Override
    public List<ParameterDTO> entitiesToDtos(List<ParameterEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        List<ParameterDTO> dtos = new ArrayList<>();
        for (ParameterEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
}
