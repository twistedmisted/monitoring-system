package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitParameterEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.impl.ParameterMapper;
import ua.kpi.mishchenko.monitoringsystem.repository.UnitParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.service.UnitParameterService;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UnitParameterServiceImpl implements UnitParameterService {

    private final UnitParameterRepository unitParameterRepository;
    private final ParameterMapper parameterMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getAllBeanNameParametersByUnitId(Long unitId) {
        return parameterMapper.upEntitiesToBeanNameString(unitParameterRepository.findAllByUnitId(unitId));
    }

    @Override
    public List<ParameterDTO> getAllParametersByUnitId(Long unitId) {
        return parameterMapper.upEntitiesToParameterDto(unitParameterRepository.findAllByUnitId(unitId));
    }

    @Override
    public void addYear(Long unitId, String parameterName) {
        UnitParameterEntity unitParameter = unitParameterRepository.findByUnitIdAndParameterBeanName(unitId, parameterName)
                .orElse(null);
        if (isNull(unitParameter)) {
            return;
        }
        unitParameter.setAmountYear(unitParameter.getAmountYear() + 1);
        unitParameterRepository.save(unitParameter);
    }

    @Override
    public void removeYear(Long unitId, String parameterName, Integer year) {
        UnitParameterEntity unitParameter = unitParameterRepository.findByUnitIdAndParameterBeanName(unitId, parameterName)
                .orElse(null);
        if (isNull(unitParameter)) {
            return;
        }
        unitParameter.setAmountYear(unitParameter.getAmountYear() - 1);
        unitParameterRepository.save(unitParameter);
        jdbcTemplate.update("DELETE FROM " + parameterName + " WHERE unit_id = ? AND year = ?", unitId, year);
    }
}
