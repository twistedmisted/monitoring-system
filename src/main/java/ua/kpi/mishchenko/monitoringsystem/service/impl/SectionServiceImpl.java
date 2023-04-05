package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.EnterpriseDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.SectionDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.SetParametersRequest;
import ua.kpi.mishchenko.monitoringsystem.entity.SectionEntity;
import ua.kpi.mishchenko.monitoringsystem.entity.SectionParameterEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.impl.SectionMapper;
import ua.kpi.mishchenko.monitoringsystem.repository.ParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.repository.SectionParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.repository.SectionRepository;
import ua.kpi.mishchenko.monitoringsystem.service.SectionService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final SectionParameterRepository sectionParameterRepository;
    private final ParameterRepository parameterRepository;
    private final SectionMapper sectionMapper;

    @Override
    public SectionDTO getSectionById(Long sectionId) {
        return sectionMapper.entityToDto(sectionRepository.findById(sectionId).orElse(null));
    }

    @Override
    public SectionDTO createEnterprise(SectionDTO sectionDTO) {
        if (isNull(sectionDTO) || existsByName(sectionDTO.getName())) {
            return null;
        }
        return saveSection(sectionDTO);
    }

    @Override
    public SectionDTO createDepartment(SectionDTO sectionDTO) {
        // TODO: need to set parent id somewhere
        if (isNull(sectionDTO) || existsByParentIdAndName(sectionDTO.getParentId(), sectionDTO.getName())) {
            return null;
        }
        return saveSection(sectionDTO);
    }

    @Override
    public boolean isEnterpriseById(Long enterpriseId) {
        SectionDTO sectionDTO = this.getSectionById(enterpriseId);
        return sectionDTO != null && !hasParent(sectionDTO);
    }

    @Override
    public List<EnterpriseDTO> getAllEnterprises() {
        Long parentId = 0L;
        List<SectionEntity> sections = sectionRepository.findAllByParentId(parentId);
        if (sections.isEmpty()) {
            return new ArrayList<>();
        }
        List<EnterpriseDTO> enterprises = new ArrayList<>();
        for (SectionEntity section : sections) {
            EnterpriseDTO enterprise = new EnterpriseDTO();
            enterprise.setId(section.getId());
            enterprise.setName(section.getName());
            enterprise.setDepartments(sectionMapper.entitiesToDtos(sectionRepository.findAllByParentId(section.getId())));
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
        if (sectionParameterRepository.existsBySectionIdAndParameterBeanName(departmentId, beanName)) {
            if (!isParameterChecked.get()) {
                sectionParameterRepository.deleteBySectionIdAndParameterBeanName(departmentId, beanName);
            }
        } else {
            if (isParameterChecked.get()) {
                SectionParameterEntity up = new SectionParameterEntity();
                up.setSection(sectionRepository.findById(departmentId).orElseThrow());
                up.setParameter(parameterRepository.findByBeanName(beanName).orElseThrow());
                up.setAmountYear(10);
                sectionParameterRepository.save(up);
            }
        }
    }

    private static boolean hasParent(SectionDTO sectionDTO) {
        return sectionDTO.getParentId() != 0;
    }

    private SectionDTO saveSection(SectionDTO sectionDTO) {
        return sectionMapper.entityToDto(sectionRepository.save(sectionMapper.dtoToEntity(sectionDTO)));
    }

    private boolean existsByName(String name) {
        return sectionRepository.existsByNameAndParentId(name, 0L);
    }

    private boolean existsByParentIdAndName(Long parentId, String name) {
        return sectionRepository.existsByParentIdAndName(parentId, name);
    }
}
