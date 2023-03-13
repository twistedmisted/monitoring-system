package ua.kpi.mishchenko.monitoringsystem.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.mishchenko.monitoringsystem.dto.TariffDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.TariffEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.Mapper;
import ua.kpi.mishchenko.monitoringsystem.repository.ParameterRepository;

import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class TariffMapper implements Mapper<TariffEntity, TariffDTO> {

    private final ParameterRepository parameterRepository;

    @Override
    public TariffEntity dtoToEntity(TariffDTO dto) {
        if (isNull(dto)) {
            return null;
        }
        TariffEntity entity = new TariffEntity();
        entity.setId(dto.getId());
        entity.setPrice(dto.getPrice());
        entity.setParameter(parameterRepository.findById(dto.getId()).get());
        return entity;
    }

    @Override
    public TariffDTO entityToDto(TariffEntity entity) {
        if (isNull(entity)) {
            return null;
        }
        TariffDTO dto = new TariffDTO();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    @Override
    public List<TariffEntity> dtosToEntities(List<TariffDTO> dtos) {
        return null;
    }

    @Override
    public List<TariffDTO> entitiesToDtos(List<TariffEntity> entities) {
        return null;
    }
}
