package ua.kpi.mishchenko.monitoringsystem.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.mishchenko.monitoringsystem.dto.UnitDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.Mapper;

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
        return null;
    }

    @Override
    public UnitDTO entityToDto(UnitEntity entity) {
        return null;
    }

    @Override
    public List<UnitEntity> dtosToEntities(List<UnitDTO> dtos) {
        return null;
    }

    @Override
    public List<UnitDTO> entitiesToDtos(List<UnitEntity> entities) {
        return null;
    }
}
