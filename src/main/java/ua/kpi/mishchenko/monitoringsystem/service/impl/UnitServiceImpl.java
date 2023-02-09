package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.UnitDTO;
import ua.kpi.mishchenko.monitoringsystem.mapper.impl.UnitMapper;
import ua.kpi.mishchenko.monitoringsystem.repository.UnitRepository;
import ua.kpi.mishchenko.monitoringsystem.service.UnitService;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    @Override
    public UnitDTO createEnterprise(UnitDTO unitDTO) {
        if (existsByName(unitDTO.getName())) {
            return null;
        }
        return saveUnit(unitDTO);
    }

    @Override
    public UnitDTO createDepartment(UnitDTO unitDTO) {
        // TODO: need to set parent id somewhere
        if (existsByParentIdAndName(unitDTO.getParentId(), unitDTO.getName())) {
            return null;
        }
        return saveUnit(unitDTO);
    }

    private UnitDTO saveUnit(UnitDTO unitDTO) {
        return unitMapper.entityToDto(unitRepository.save(unitMapper.dtoToEntity(unitDTO)));
    }

    private boolean existsByName(String name) {
        return unitRepository.existsByName(name);
    }

    private boolean existsByParentIdAndName(Long parentId, String name) {
        return unitRepository.existsByParentIdAndName(parentId, name);
    }
}
