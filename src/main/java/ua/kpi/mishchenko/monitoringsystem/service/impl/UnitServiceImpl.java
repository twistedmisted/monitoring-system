package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.EnterpriseDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.SetParametersRequest;
import ua.kpi.mishchenko.monitoringsystem.dto.UnitDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitEntity;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitParameterEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.impl.UnitMapper;
import ua.kpi.mishchenko.monitoringsystem.repository.ParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.repository.UnitParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.repository.UnitRepository;
import ua.kpi.mishchenko.monitoringsystem.service.UnitService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final UnitParameterRepository unitParameterRepository;
    private final ParameterRepository parameterRepository;
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
        setDepartmentParameter(departmentId, "volumes_electricity_consumption", setParametersRequest::isVolumesElectricityConsumption);
        setDepartmentParameter(departmentId, "electricity_costs", setParametersRequest::isElectricityCosts);
        setDepartmentParameter(departmentId, "volumes_electricity_production", setParametersRequest::isVolumesElectricityProduction);
        setDepartmentParameter(departmentId, "costs_electricity_production", setParametersRequest::isCostsElectricityProduction);
        setDepartmentParameter(departmentId, "volumes_thermal_energy_productionSale", setParametersRequest::isVolumesThermalEnergyProductionSale);
        setDepartmentParameter(departmentId, "volumes_thermal_energySales", setParametersRequest::isVolumesThermalEnergySales);
        setDepartmentParameter(departmentId, "volumes_thermal_energy_consumption", setParametersRequest::isVolumesThermalEnergyConsumption);
        setDepartmentParameter(departmentId, "heat_energy_costs_own_needs", setParametersRequest::isHeatEnergyCostsOwnNeeds);
        setDepartmentParameter(departmentId, "volumes_gas_consumption", setParametersRequest::isVolumesGasConsumption);
        setDepartmentParameter(departmentId, "gas_expenses", setParametersRequest::isGasExpenses);
        setDepartmentParameter(departmentId, "volumes_water_consumption", setParametersRequest::isVolumesWaterConsumption);
        setDepartmentParameter(departmentId, "water_costs", setParametersRequest::isWaterCosts);
        setDepartmentParameter(departmentId, "volumes_coal_consumption", setParametersRequest::isVolumesCoalConsumption);
        setDepartmentParameter(departmentId, "coal_costs", setParametersRequest::isCoalCosts);
        setDepartmentParameter(departmentId, "fuel_oil_consumption_volumes", setParametersRequest::isFuelOilConsumptionVolumes);
        setDepartmentParameter(departmentId, "costs_fuel_oil", setParametersRequest::isCostsFuelOil);
        setDepartmentParameter(departmentId, "volumes_diesel_fuel_consumption", setParametersRequest::isVolumesDieselFuelConsumption);
        setDepartmentParameter(departmentId, "diesel_fuel_costs", setParametersRequest::isDieselFuelCosts);
        setDepartmentParameter(departmentId, "volumes_gasoline_consumption", setParametersRequest::isVolumesGasolineConsumption);
        setDepartmentParameter(departmentId, "gasoline_expenses", setParametersRequest::isGasolineExpenses);
        setDepartmentParameter(departmentId, "volumes_consumption_lubricants", setParametersRequest::isVolumesConsumptionLubricants);
        setDepartmentParameter(departmentId, "costs_lubricants", setParametersRequest::isCostsLubricants);
        setDepartmentParameter(departmentId, "drainage_volumes", setParametersRequest::isDrainageVolumes);
        setDepartmentParameter(departmentId, "drainage_costs", setParametersRequest::isDrainageCosts);
        setDepartmentParameter(departmentId, "volumes_solid_household_waste", setParametersRequest::isVolumesSolidHouseholdWaste);
        setDepartmentParameter(departmentId, "costs_disposal_solid_household_waste", setParametersRequest::isCostsDisposalSolidHouseholdWaste);
        setDepartmentParameter(departmentId, "ambient_temperature", setParametersRequest::isAmbientTemperature);
        setDepartmentParameter(departmentId, "volumes_production_products_and_services", setParametersRequest::isVolumesProductionProductsAndServices);
        setDepartmentParameter(departmentId, "CO_emissions", setParametersRequest::isCOEmissions);
    }

    private void setDepartmentParameter(Long departmentId, String beanName, Supplier<Boolean> isParameterChecked) {
        if (unitParameterRepository.existsByUnitIdAndParameterBeanName(departmentId, beanName)) {
            if (!isParameterChecked.get()) {
                unitParameterRepository.deleteByUnitIdAndParameterBeanName(departmentId, beanName);
            }
        } else {
            if (isParameterChecked.get()) {
                UnitParameterEntity up = new UnitParameterEntity();
                up.setUnit(unitRepository.findById(departmentId).orElseThrow());
                up.setParameter(parameterRepository.findByBeanName(beanName).orElseThrow());
                unitParameterRepository.save(up);
            }
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
