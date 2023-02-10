package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.EnterpriseDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.SetParametersRequest;
import ua.kpi.mishchenko.monitoringsystem.dto.UnitDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.impl.UnitMapper;
import ua.kpi.mishchenko.monitoringsystem.repository.UnitRepository;
import ua.kpi.mishchenko.monitoringsystem.service.UnitService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    @Override
    public UnitDTO getUnitById(Long id) {
        return unitMapper.entityToDto(unitRepository.findById(id).orElse(null));
    }

    @Override
    public UnitDTO createEnterprise(UnitDTO unitDTO) {
        if (isNull(unitDTO) || existsByName(unitDTO.getName())) {
            return null;
        }
        return saveUnit(unitDTO);
    }

    @Override
    public UnitDTO createDepartment(UnitDTO unitDTO) {
        // TODO: need to set parent id somewhere
        if (isNull(unitDTO) || existsByParentIdAndName(unitDTO.getParentId(), unitDTO.getName())) {
            return null;
        }
        return saveUnit(unitDTO);
    }

    @Override
    public boolean isEnterpriseById(Long enterpriseId) {
        UnitDTO unitDTO = getUnitById(enterpriseId);
        return unitDTO != null && !hasParent(unitDTO);
    }

    @Override
    public List<EnterpriseDTO> getAllEnterprises() {
        Long parentId = 0L;
        List<UnitEntity> units = unitRepository.findAllByParentId(parentId);
        if (units.isEmpty()) {
            return new ArrayList<>();
        }
        List<EnterpriseDTO> enterprises = new ArrayList<>();
        for (UnitEntity unit : units) {
            EnterpriseDTO enterprise = new EnterpriseDTO();
            enterprise.setId(unit.getId());
            enterprise.setName(unit.getName());
            enterprise.setDepartments(unitMapper.entitiesToDtos(unitRepository.findAllByParentId(unit.getId())));
            enterprises.add(enterprise);
        }
        return enterprises;
    }

    @Override
    public void setDepartmentParameters(Long departmentId, SetParametersRequest setParametersRequest) {
        UnitEntity unit = unitRepository.findById(departmentId).orElse(null);
        if (isNull(unit)) {
            return;
        }

        // TODO: need continue
        if (setParametersRequest.isVolumesElectricityConsumption()) {
            unit.getParameters()
        }
    }

    private static boolean hasParent(UnitDTO unitDTO) {
        return unitDTO.getParentId() != 0;
    }

    private UnitDTO saveUnit(UnitDTO unitDTO) {
        return unitMapper.entityToDto(unitRepository.save(unitMapper.dtoToEntity(unitDTO)));
    }

    private boolean existsByName(String name) {
        return unitRepository.existsByNameAndParentId(name, 0L);
    }

    private boolean existsByParentIdAndName(Long parentId, String name) {
        return unitRepository.existsByParentIdAndName(parentId, name);
    }
}
