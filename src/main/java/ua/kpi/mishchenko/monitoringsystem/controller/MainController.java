package ua.kpi.mishchenko.monitoringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kpi.mishchenko.monitoringsystem.dto.EnterpriseDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.InputDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.OutputData;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.SetParametersRequest;
import ua.kpi.mishchenko.monitoringsystem.dto.UnitDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.WorkingDaysDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.YearValue;
import ua.kpi.mishchenko.monitoringsystem.service.ParameterBaseService;
import ua.kpi.mishchenko.monitoringsystem.service.UnitParameterService;
import ua.kpi.mishchenko.monitoringsystem.service.UnitService;
import ua.kpi.mishchenko.monitoringsystem.service.WorkingDaysService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Controller
@RequestMapping("/units")
@RequiredArgsConstructor
public class MainController {

    private final UnitService unitService;
    private final UnitParameterService unitParameterService;
    private final ParameterBaseService parameterBaseService;
    private final WorkingDaysService workingDaysService;

    @GetMapping
    public String getHomePage(Model model) {
        List<EnterpriseDTO> enterprises = unitService.getAllEnterprises();
        model.addAttribute("enterprises", enterprises);
        return "home";
    }

    @GetMapping("/enterprises")
    public String getCreateEnterprisePage(Model model) {
        model.addAttribute("enterprise", new UnitDTO());
        return "create-enterprise";
    }

    @PostMapping("/enterprises")
    public String createEnterprise(@ModelAttribute UnitDTO enterprise) {
        if (enterprise != null) {
            enterprise.setParentId(0L);
            unitService.createEnterprise(enterprise);
        }
        return "redirect:/units";
    }

    @GetMapping("/enterprises/{id}/departments")
    public String getCreateDepartmentPage(@PathVariable(name = "id") Long enterpriseId, Model model) {
        if (unitService.isEnterpriseById(enterpriseId)) {
            model.addAttribute("enterpriseId", enterpriseId);
            model.addAttribute("department", new UnitDTO());
        } else {
            model.addAttribute("message", "Організацію не знайдено!");
        }
        return "create-department";
    }

    @PostMapping("/enterprises/{enterpriseId}/departments")
    public String createDepartment(@PathVariable(name = "enterpriseId") Long enterpriseId, @ModelAttribute UnitDTO department) {
        department.setParentId(enterpriseId);
        unitService.createDepartment(department);
        return "redirect:/units";
    }

    @GetMapping("/enterprises/{enterpriseId}/departments/{departmentId}/parameters")
    public String getDepartmentParametersPage(@PathVariable Long enterpriseId,
                                              @PathVariable Long departmentId,
                                              Model model) {
        UnitDTO department = unitService.getUnitById(departmentId);
        List<String> parameters = unitParameterService.getAllBeanNameParametersByUnitId(departmentId);
        SetParametersRequest setParametersRequest = createSetParametersRequest(parameters);
        if (department == null) {
            model.addAttribute("message", "Відділу не знайдено!");
        } else {
            model.addAttribute("department", department);
            model.addAttribute("enterpriseId", enterpriseId);
            model.addAttribute("parameters", setParametersRequest);
        }
        return "parameters";
    }

    private SetParametersRequest createSetParametersRequest(List<String> parameters) {
        SetParametersRequest setParametersRequest = new SetParametersRequest();
        for (String parameter : parameters) {
            setParameter(parameter, "volumes_electricity_consumption", setParametersRequest::setVolumesElectricityConsumption);
            setParameter(parameter, "electricity_costs", setParametersRequest::setElectricityCosts);
            setParameter(parameter, "volumes_electricity_production", setParametersRequest::setVolumesElectricityProduction);
            setParameter(parameter, "costs_electricity_production", setParametersRequest::setCostsElectricityProduction);
            setParameter(parameter, "volumes_thermal_energy_productionSale", setParametersRequest::setVolumesThermalEnergyProductionSale);
            setParameter(parameter, "volumes_thermal_energySales", setParametersRequest::setVolumesThermalEnergySales);
            setParameter(parameter, "volumes_thermal_energy_consumption", setParametersRequest::setVolumesThermalEnergyConsumption);
            setParameter(parameter, "heat_energy_costs_own_needs", setParametersRequest::setHeatEnergyCostsOwnNeeds);
            setParameter(parameter, "volumes_gas_consumption", setParametersRequest::setVolumesGasConsumption);
            setParameter(parameter, "gas_expenses", setParametersRequest::setGasExpenses);
            setParameter(parameter, "volumes_water_consumption", setParametersRequest::setVolumesWaterConsumption);
            setParameter(parameter, "water_costs", setParametersRequest::setWaterCosts);
            setParameter(parameter, "volumes_coal_consumption", setParametersRequest::setVolumesCoalConsumption);
            setParameter(parameter, "coal_costs", setParametersRequest::setCoalCosts);
            setParameter(parameter, "fuel_oil_consumption_volumes", setParametersRequest::setFuelOilConsumptionVolumes);
            setParameter(parameter, "costs_fuel_oil", setParametersRequest::setCostsFuelOil);
            setParameter(parameter, "volumes_diesel_fuel_consumption", setParametersRequest::setVolumesDieselFuelConsumption);
            setParameter(parameter, "diesel_fuel_costs", setParametersRequest::setDieselFuelCosts);
            setParameter(parameter, "volumes_gasoline_consumption", setParametersRequest::setVolumesGasolineConsumption);
            setParameter(parameter, "gasoline_expenses", setParametersRequest::setGasolineExpenses);
            setParameter(parameter, "volumes_consumption_lubricants", setParametersRequest::setVolumesConsumptionLubricants);
            setParameter(parameter, "costs_lubricants", setParametersRequest::setCostsLubricants);
            setParameter(parameter, "drainage_volumes", setParametersRequest::setDrainageVolumes);
            setParameter(parameter, "drainage_costs", setParametersRequest::setDrainageCosts);
            setParameter(parameter, "volumes_solid_household_waste", setParametersRequest::setVolumesSolidHouseholdWaste);
            setParameter(parameter, "costs_disposal_solid_household_waste", setParametersRequest::setCostsDisposalSolidHouseholdWaste);
            setParameter(parameter, "ambient_temperature", setParametersRequest::setAmbientTemperature);
            setParameter(parameter, "volumes_production_products_and_services", setParametersRequest::setVolumesProductionProductsAndServices);
            setParameter(parameter, "CO_emissions", setParametersRequest::setCOEmissions);
        }
        return setParametersRequest;
    }

    private void setParameter(String parameter, String beanName, Consumer<Boolean> setValue) {
        if (parameter.equals(beanName)) {
            setValue.accept(true);
        }
    }

    @PostMapping("/enterprises/{enterpriseId}/departments/{departmentId}/parameters")
    public String setParameters(@PathVariable Long enterpriseId,
                                @PathVariable Long departmentId,
                                @ModelAttribute SetParametersRequest setParameters) {
        unitService.setDepartmentParameters(departmentId, setParameters);
        return "redirect:/units";
    }

    @GetMapping("/enterprises/{enterpriseId}")
    public String getInputPage(@PathVariable Long enterpriseId,
                               @RequestParam(value = "parameter-name", required = false) String parameterName,
                               Model model) {
        List<ParameterDTO> departmentParameters = unitParameterService.getAllParametersByEnterpriseId(enterpriseId);
        InputDTO tableData = new InputDTO();
        OutputData outputData = new OutputData();
        if (parameterName != null && !parameterName.isBlank()) {
            tableData = parameterBaseService.getDataForEnterpriseByParameterName(enterpriseId, parameterName);
            tableData.setParameterName(parameterName);
        } else {
            tableData.setYearValues(new ArrayList<>(Collections.nCopies(10, new YearValue())));
        }
        model.addAttribute("tableData", tableData);
        model.addAttribute("departmentParameters", departmentParameters);
        return "output";
    }

    @GetMapping("/enterprises/{enterpriseId}/year")
    public String getInputPage(@PathVariable Long enterpriseId,
                               @RequestParam(value = "value", required = false) Integer year,
                               Model model) {
        if (year == null) {
            return "output-by-year";
        }
        List<ParameterDTO> departmentParameters = unitParameterService.getAllParametersByEnterpriseId(enterpriseId);
        List<InputDTO> tableDataList = new ArrayList<>();
        for (ParameterDTO parameterName : departmentParameters) {
            InputDTO tableData = parameterBaseService.getDataForEnterpriseByParameterNameAndYear(enterpriseId, parameterName.getBeanName(), year);
            tableData.setParameterName(parameterName.getName());
            tableDataList.add(tableData);
        }
        if (tableDataList.isEmpty()) {
            model.addAttribute("message", "Дані за цей рік відсутні");
        } else {
            model.addAttribute("tableDataList", tableDataList);
            model.addAttribute("year", year);
        }
        return "output-by-year";
    }

    @GetMapping("/enterprises/{enterpriseId}/departments/{departmentId}/input")
    public String getInputPage(@PathVariable Long enterpriseId,
                               @PathVariable Long departmentId,
                               @RequestParam(value = "parameter-name", required = false) String parameterName,
                               Model model) {
        List<ParameterDTO> departmentParameters = unitParameterService.getAllParametersByUnitId(departmentId);
        InputDTO tableData = new InputDTO();
        if (parameterName != null && !parameterName.isBlank()) {
            tableData = parameterBaseService.getDataByParameterName(departmentId, parameterName);
            tableData.setParameterName(parameterName);
        } else {
            tableData.setYearValues(new ArrayList<>(Collections.nCopies(10, new YearValue())));
        }
        model.addAttribute("tableData", tableData);
        model.addAttribute("departmentParameters", departmentParameters);
        return "input";
    }

    @PostMapping("/enterprises/{enterpriseId}/departments/{departmentId}/input")
    public String saveDepartmentData(@PathVariable Long enterpriseId,
                                     @PathVariable Long departmentId,
                                     @ModelAttribute InputDTO tableData) {
        parameterBaseService.saveData(departmentId, tableData.getParameterName(), tableData);
        return "redirect:/units/enterprises/" + enterpriseId + "/departments/" + departmentId + "/input?parameter-name=" + tableData.getParameterName();
    }

    @PostMapping("/enterprises/{enterpriseId}/departments/{departmentId}/add-year")
    public String addYear(@PathVariable Long enterpriseId,
                          @PathVariable Long departmentId,
                          @RequestParam(value = "parameter-name") String parameterName) {
        unitParameterService.addYear(departmentId, parameterName);
        return "redirect:/units/enterprises/" + enterpriseId + "/departments/" + departmentId + "/input?parameter-name=" + parameterName;
    }

    @PostMapping("/enterprises/{enterpriseId}/departments/{departmentId}/remove-year")
    public String removeYear(@PathVariable Long enterpriseId,
                             @PathVariable Long departmentId,
                             @RequestParam(value = "parameter-name") String parameterName,
                             @RequestParam(value = "year") Integer year) {
        unitParameterService.removeYear(departmentId, parameterName, year);
        return "redirect:/units/enterprises/" + enterpriseId + "/departments/" + departmentId + "/input?parameter-name=" + parameterName;
    }

    @GetMapping("/enterprises/{enterpriseId}/departments/{departmentId}/working-days")
    public String getWorkingDaysPage(@PathVariable Long enterpriseId,
                                     @PathVariable Long departmentId,
                                     @RequestParam(name = "year", required = false) Integer year,
                                     Model model) {
        UnitDTO department = unitService.getUnitById(departmentId);
        WorkingDaysDTO workingDays = workingDaysService.getWorkingDaysByUnitIdAndYear(departmentId, year);
        model.addAttribute("department", department);
        model.addAttribute("workingDays", workingDays);
        return "working-days";
    }

    @PostMapping("/enterprises/{enterpriseId}/departments/{departmentId}/working-days")
    public String saveWorkingDays(@PathVariable Long enterpriseId,
                                  @PathVariable Long departmentId,
                                  @RequestParam(name = "year", required = false) Integer year,
                                  @ModelAttribute WorkingDaysDTO workingDays) {
//        UnitDTO department = unitService.getUnitById(departmentId);
        workingDays.setUnitId(departmentId);
        workingDaysService.saveWorkingDays(workingDays);
//        WorkingDaysDTO workingDays = workingDaysService.getWorkingDaysByUnitIdAndYear(departmentId, year);
//        model.addAttribute("department", department);
//        model.addAttribute("workingDays", workingDays);
        return "redirect:/units";
    }
}
