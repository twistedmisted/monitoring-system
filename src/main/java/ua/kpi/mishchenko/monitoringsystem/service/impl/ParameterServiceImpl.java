package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterWithTariff;
import ua.kpi.mishchenko.monitoringsystem.mapper.impl.ParameterMapper;
import ua.kpi.mishchenko.monitoringsystem.repository.ParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.service.ParameterService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParameterServiceImpl implements ParameterService {

    private static final Boolean HAS_TARIFF = true;

    private final ParameterRepository parameterRepository;
    private final ParameterMapper parameterMapper;

    @Override
    public ParameterWithTariff getParameterWithTariffByName(String name) {
        return parameterMapper.entityToDtoWithTariff(parameterRepository.findByBeanNameAndHasTariff(name, HAS_TARIFF)
                .orElse(null));
    }

    @Override
    public List<ParameterWithTariff> getAllParametersWithTariff() {
        return parameterMapper.entitiesToDtosWithTariff(parameterRepository.findAllByHasTariff(HAS_TARIFF));
    }
}
