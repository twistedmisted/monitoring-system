package ua.kpi.mishchenko.monitoringsystem.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterWithTariff;
import ua.kpi.mishchenko.monitoringsystem.entity.ParameterEntity;
import ua.kpi.mishchenko.monitoringsystem.entity.TariffEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.impl.TariffMapper;
import ua.kpi.mishchenko.monitoringsystem.repository.ParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.repository.TariffRepository;
import ua.kpi.mishchenko.monitoringsystem.service.TariffService;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {

    private final ParameterRepository parameterRepository;
    private final TariffRepository tariffRepository;
    private final TariffMapper tariffMapper;

    @Override
    @Transactional
    public void saveTariff(String name, ParameterWithTariff parameterWithTariff) {
        ParameterEntity parameterEntity = parameterRepository.findByBeanName(name).orElseThrow();
        parameterWithTariff.getTariff().setId(parameterEntity.getId());
        parameterEntity.setTariff(tariffMapper.dtoToEntity(parameterWithTariff.getTariff()));
        parameterRepository.save(parameterEntity);
//        TariffEntity entity = tariffMapper.dtoToEntity(parameterWithTariff.getTariff());
//        tariffRepository.save(entity);
    }
}
