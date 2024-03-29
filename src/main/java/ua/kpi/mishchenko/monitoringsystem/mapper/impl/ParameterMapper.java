package ua.kpi.mishchenko.monitoringsystem.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterWithTariff;
import ua.kpi.mishchenko.monitoringsystem.entity.ParameterEntity;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitParameterEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class ParameterMapper implements Mapper<ParameterEntity, ParameterDTO> {

    private final TariffMapper tariffMapper;

    @Override
    public ParameterEntity dtoToEntity(ParameterDTO dto) {
        if (dto == null) {
            return null;
        }
        ParameterEntity entity = new ParameterEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setBeanName(dto.getBeanName());
        entity.setHasTariff(dto.getHasTariff());
        entity.setCostsName(dto.getCostsName());
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
        dto.setBeanName(entity.getBeanName());
        dto.setHasTariff(entity.getHasTariff());
        dto.setCostsName(entity.getCostsName());
        return dto;
    }

    public ParameterDTO entityToDto(UnitParameterEntity entity) {
        if (entity == null) {
            return null;
        }
        ParameterDTO dto = new ParameterDTO();
        dto.setId(entity.getParameter().getId());
        dto.setName(entity.getParameter().getName());
        dto.setBeanName(entity.getParameter().getBeanName());
        dto.setHasTariff(entity.getParameter().getHasTariff());
        dto.setCostsName(entity.getParameter().getCostsName());
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

    public List<ParameterDTO> upEntitiesToParameterDto(List<UnitParameterEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        List<ParameterDTO> dtos = new ArrayList<>();
        for (UnitParameterEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    public List<String> upEntitiesToBeanNameString(List<UnitParameterEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> beanNames = new ArrayList<>();
        for (UnitParameterEntity entity : entities) {
            beanNames.add(entity.getParameter().getBeanName());
        }
        return beanNames;
    }

    public ParameterWithTariff entityToDtoWithTariff(ParameterEntity entity) {
        if (isNull(entity)) {
            return null;
        }
        ParameterWithTariff dto = new ParameterWithTariff();
        dto.setParameter(entityToDto(entity));
        dto.setTariff(tariffMapper.entityToDto(entity.getTariff()));
        return dto;
    }

    public List<ParameterWithTariff> entitiesToDtosWithTariff(List<ParameterEntity> entities) {
        if (isNull(entities) || entities.isEmpty()) {
            return new ArrayList<>();
        }
        List<ParameterWithTariff> dtos = new ArrayList<>();
        for (ParameterEntity entity : entities) {
            dtos.add(entityToDtoWithTariff(entity));
        }
        return dtos;
    }
}
