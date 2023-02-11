package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterDTO;
import ua.kpi.mishchenko.monitoringsystem.mapper.impl.ParameterMapper;
import ua.kpi.mishchenko.monitoringsystem.repository.UnitParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.service.UnitParameterService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitParameterServiceImpl implements UnitParameterService {

    private final UnitParameterRepository unitParameterRepository;
    private final ParameterMapper parameterMapper;

    @Override
    public List<String> getAllBeanNameParametersByUnitId(Long unitId) {
        return parameterMapper.upEntitiesToBeanNameString(unitParameterRepository.findAllByUnitId(unitId));
    }

    @Override
    public List<ParameterDTO> getAllParametersByUnitId(Long unitId) {
        return parameterMapper.upEntitiesToParameterDto(unitParameterRepository.findAllByUnitId(unitId));
    }
}
