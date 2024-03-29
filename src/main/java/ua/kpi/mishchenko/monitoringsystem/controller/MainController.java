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
import ua.kpi.mishchenko.monitoringsystem.dto.CommentDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.EnterpriseDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.EnterprisePieChartDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.InputDataDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.InputYearInfo;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterYearsInfo;
import ua.kpi.mishchenko.monitoringsystem.dto.PieChartDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.SetParametersRequest;
import ua.kpi.mishchenko.monitoringsystem.dto.TableData;
import ua.kpi.mishchenko.monitoringsystem.dto.UnitDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.WorkingDaysByYear;
import ua.kpi.mishchenko.monitoringsystem.dto.YearInfo;
import ua.kpi.mishchenko.monitoringsystem.entity.ParameterEntity;
import ua.kpi.mishchenko.monitoringsystem.repository.ParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.service.CommentService;
import ua.kpi.mishchenko.monitoringsystem.service.ParameterBaseService;
import ua.kpi.mishchenko.monitoringsystem.service.ParameterService;
import ua.kpi.mishchenko.monitoringsystem.service.UnitParameterService;
import ua.kpi.mishchenko.monitoringsystem.service.UnitService;
import ua.kpi.mishchenko.monitoringsystem.service.WorkingDaysService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static ua.kpi.mishchenko.monitoringsystem.controller.CommentController.PIE_CHART;

@Controller
@RequestMapping("/units")
@RequiredArgsConstructor
public class MainController {

    private final UnitService unitService;
    private final UnitParameterService unitParameterService;
    private final ParameterBaseService parameterBaseService;
    private final WorkingDaysService workingDaysService;
    private final ParameterRepository parameterRepository;
    private final CommentService commentService;
    private final ParameterService parameterService;

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

    @GetMapping("/enterprises/{enterpriseId}/output")
    public String getInputPage(@PathVariable Long enterpriseId,
                               @RequestParam(value = "parameter-name", required = false) String parameterName,
                               Model model) {
        List<ParameterDTO> departmentParameters = unitParameterService.getAllParametersByEnterpriseId(enterpriseId);
        ParameterYearsInfo consumptionTableData = new ParameterYearsInfo();
        ParameterYearsInfo costsTableData = new ParameterYearsInfo();
        if (parameterName != null && !parameterName.isBlank()) {
            consumptionTableData = parameterBaseService.getDataForEnterpriseByParameterName(enterpriseId, parameterName);
            if (parameterRepository.existsByBeanNameAndHasTariff(parameterName, true)) {
                costsTableData = parameterBaseService.getCostsDataForEnterpriseByParameterName(enterpriseId, parameterName);
            }
        }
        model.addAttribute("consumptionTableData", consumptionTableData);
        model.addAttribute("costsTableData", costsTableData);
        model.addAttribute("departmentParameters", departmentParameters);
        return "enterprise/output";
    }

    @GetMapping("/enterprises/{enterpriseId}/output/year")
    public String getInputPage(@PathVariable Long enterpriseId,
                               @RequestParam(value = "value", required = false) Integer year,
                               Model model) {
        if (year == null) {
            return "enterprise/output-by-year";
        }
        List<ParameterDTO> departmentParameters = unitParameterService.getAllParametersByEnterpriseId(enterpriseId);
        List<TableData> tableDataList = new ArrayList<>();
        for (ParameterDTO parameterName : departmentParameters) {
            TableData tableData = parameterBaseService.getDataForEnterpriseByParameterNameAndYear(enterpriseId, parameterName.getBeanName(), year);
            tableData.setParameterName(parameterName.getName());
            tableDataList.add(tableData);
            if (parameterRepository.existsByBeanNameAndHasTariff(parameterName.getBeanName(), true)) {
                TableData costsTableData = parameterBaseService.getCostsDataForEnterpriseByParameterNameAndYear(enterpriseId, parameterName.getBeanName(), year);
                costsTableData.setParameterName("Витрати");
                tableDataList.add(costsTableData);
            }
        }
        if (tableDataList.isEmpty()) {
            model.addAttribute("message", "Дані за цей рік відсутні");
        } else {
            model.addAttribute("tableDataList", tableDataList);
            model.addAttribute("year", year);
        }
        return "enterprise/output-by-year";
    }

    @GetMapping("/enterprises/{enterpriseId}/departments/{departmentId}/output")
    public String getOutputDepartmentPage(@PathVariable Long enterpriseId,
                                          @PathVariable Long departmentId,
                                          @RequestParam(value = "parameter-name", required = false) String parameterName,
                                          Model model) {
        List<ParameterDTO> departmentParameters = unitParameterService.getAllParametersByUnitId(departmentId);
        TableData consumptionTableData = new TableData();
        TableData costsTableData = new TableData();
        if (parameterName != null && !parameterName.isBlank()) {
            consumptionTableData = parameterBaseService.getDataByParameterNameWithWorkingDays(departmentId, parameterName);
            if (parameterRepository.existsByBeanNameAndHasTariff(parameterName, true)) {
                costsTableData = parameterBaseService.getCostsDataByParameterNameWithWorkingDays(departmentId, parameterName);
            }
        }
        model.addAttribute("consumptionTableData", consumptionTableData);
        model.addAttribute("costsTableData", costsTableData);
        model.addAttribute("departmentParameters", departmentParameters);
        model.addAttribute("enterpriseId", enterpriseId);
        model.addAttribute("departmentId", departmentId);
        return "department/output";
    }

    @GetMapping("/enterprises/{enterpriseId}/departments/{departmentId}/input")
    public String getInputPage(@PathVariable Long enterpriseId,
                               @PathVariable Long departmentId,
                               @RequestParam(value = "parameter-name", required = false) String parameterName,
                               Model model) {
        List<ParameterDTO> departmentParameters = unitParameterService.getAllParametersByUnitId(departmentId);
        InputDataDTO tableData = new InputDataDTO();
        if (parameterName != null && !parameterName.isBlank()) {
            tableData = parameterBaseService.getDataByParameterName(departmentId, parameterName);
            tableData.setParameterName(parameterName);
        } else {
            tableData.setYearInfos(new ArrayList<>(Collections.nCopies(10, new InputYearInfo())));
        }
        model.addAttribute("tableData", tableData);
        model.addAttribute("departmentParameters", departmentParameters);
        return "department/input";
    }

    @PostMapping("/enterprises/{enterpriseId}/departments/{departmentId}/input")
    public String saveDepartmentData(@PathVariable Long enterpriseId,
                                     @PathVariable Long departmentId,
                                     @ModelAttribute InputDataDTO tableData) {
        parameterBaseService.saveData(departmentId, tableData);
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
        WorkingDaysByYear workingDays = workingDaysService.getAllWorkingDaysByUnitIdAndYear(departmentId, year);
        model.addAttribute("department", department);
        model.addAttribute("workingDays", workingDays);
        return "working-days";
    }

    @PostMapping("/enterprises/{enterpriseId}/departments/{departmentId}/working-days")
    public String saveWorkingDays(@PathVariable Long enterpriseId,
                                  @PathVariable Long departmentId,
                                  @RequestParam(name = "year", required = false) Integer year,
                                  @ModelAttribute WorkingDaysByYear workingDays) {
//        UnitDTO department = unitService.getUnitById(departmentId);
        workingDays.setUnitId(departmentId);
        workingDaysService.saveWorkingDays(workingDays);
//        WorkingDaysDTO workingDays = workingDaysService.getWorkingDaysByUnitIdAndYear(departmentId, year);
//        model.addAttribute("department", department);
//        model.addAttribute("workingDays", workingDays);
        return "redirect:/units/enterprises/" + enterpriseId + "/departments/" + departmentId + "/working-days?year=" + workingDays.getYear();
    }

    @GetMapping("/enterprises/{enterpriseId}/departments/{departmentId}/line-chart")
    public String getMonthChart(@PathVariable Long enterpriseId,
                                @PathVariable Long departmentId,
                                @RequestParam(value = "parameter-name", required = false) String parameterName,
                                Model model) {
        List<ParameterDTO> departmentParameters = unitParameterService.getAllParametersByUnitId(departmentId);
        TableData consumptionTableData = new TableData();
        TableData costsTableData = new TableData();
        CommentDTO consumptionComment = new CommentDTO();
        CommentDTO costsComment = new CommentDTO();
        String consumptionTitle = null;
        String costsTitle = null;
        if (parameterName != null && !parameterName.isBlank()) {
            consumptionTableData = parameterBaseService.getDataByParameterNameWithWorkingDays(departmentId, parameterName);
            consumptionComment = commentService.getCommentByDepartmentIdAndParameterName(departmentId, parameterName);
            consumptionTitle = "Середньодобове за місяць \"" + parameterRepository.findByBeanName(parameterName).orElse(new ParameterEntity()).getName() + "\"";
            if (parameterRepository.existsByBeanNameAndHasTariff(parameterName, true)) {
                costsTableData = parameterBaseService.getCostsDataByParameterNameWithWorkingDays(departmentId, parameterName);
                costsComment = commentService.getCommentByDepartmentIdAndParameterName(departmentId, parameterName + "_costs");
                costsTitle = "Середньодобове за рік \"" + parameterRepository.findByBeanName(parameterName).orElse(new ParameterEntity()).getCostsName() + "\"";
            }
        }
        model.addAttribute("consumptionTableData", consumptionTableData);
        model.addAttribute("costsTableData", costsTableData);
        model.addAttribute("departmentParameters", departmentParameters);
        model.addAttribute("enterpriseId", enterpriseId);
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("consumptionComment", consumptionComment);
        model.addAttribute("costsComment", costsComment);
        model.addAttribute("consumptionTitle", consumptionTitle);
        model.addAttribute("costsTitle", costsTitle);
        return "department/month-chart";
    }

    @GetMapping("/enterprises/{enterpriseId}/line-chart")
    public String getEnterpriseLineChart(@PathVariable Long enterpriseId,
                                         @RequestParam(value = "parameter-name", required = false) String parameterName,
                                         Model model) {
        List<ParameterDTO> departmentParameters = unitParameterService.getAllParametersByEnterpriseId(enterpriseId);
        ParameterYearsInfo consumptionTableData = new ParameterYearsInfo();
        ParameterYearsInfo costsTableData = new ParameterYearsInfo();
        CommentDTO consumptionComment = new CommentDTO();
        CommentDTO costsComment = new CommentDTO();
        String consumptionTitle = null;
        String costsTitle = null;
        if (parameterName != null && !parameterName.isBlank()) {
            consumptionTableData = parameterBaseService.getDataForEnterpriseByParameterName(enterpriseId, parameterName);
            consumptionComment = commentService.getCommentByDepartmentIdAndParameterName(enterpriseId, parameterName);
            consumptionTitle = "Середньодобове за місяць \"" + parameterRepository.findByBeanName(parameterName).orElse(new ParameterEntity()).getName() + "\"";
            if (parameterRepository.existsByBeanNameAndHasTariff(parameterName, true)) {
                costsTableData = parameterBaseService.getCostsDataForEnterpriseByParameterName(enterpriseId, parameterName);
                costsComment = commentService.getCommentByDepartmentIdAndParameterName(enterpriseId, parameterName + "_costs");
                costsTitle = "Середньодобове за рік \"" + parameterRepository.findByBeanName(parameterName).orElse(new ParameterEntity()).getCostsName() + "\"";
            }
        }
        model.addAttribute("consumptionTableData", consumptionTableData);
        model.addAttribute("costsTableData", costsTableData);
        model.addAttribute("departmentParameters", departmentParameters);
        model.addAttribute("consumptionComment", consumptionComment);
        model.addAttribute("costsComment", costsComment);
        model.addAttribute("consumptionTitle", consumptionTitle);
        model.addAttribute("costsTitle", costsTitle);
        return "enterprise/month-chart";
    }

    @GetMapping("/enterprises/{enterpriseId}/departments/{departmentId}/pie-chart")
    public String getPieChart(@PathVariable Long enterpriseId,
                              @PathVariable Long departmentId,
                              @RequestParam(required = false) Integer year,
                              Model model) {
        PieChartDTO pieChartDTO = new PieChartDTO();
        CommentDTO comment = new CommentDTO();
        String chartTitle = null;
        if (year != null) {
            List<ParameterDTO> departmentParameters = unitParameterService.getAllParametersByUnitId(departmentId);
            for (ParameterDTO parameterDTO : departmentParameters) {
                String parameterName = parameterDTO.getBeanName();
                if (parameterRepository.existsByBeanNameAndHasTariff(parameterName, true)) {
                    YearInfo tempYearInfo = parameterBaseService.getDataByParameterNameWithWorkingDaysByYear(departmentId, parameterName, year);
                    pieChartDTO.addYearInfo(parameterService.getParameterByBeanName(parameterName).getCostsName(), tempYearInfo);
                    comment = commentService.getCommentByDepartmentIdAndParameterName(departmentId, PIE_CHART + year);
                }
            }
            chartTitle = "Енергобаланс по відділу \"" + unitService.getUnitById(departmentId).getName() + "\" за " + year + " рік";
        }
        model.addAttribute("pieChart", pieChartDTO);
        model.addAttribute("enterpriseId", enterpriseId);
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("comment", comment);
        model.addAttribute("year", year);
        model.addAttribute("chartTitle", chartTitle);
        return "department/pie-chart";
    }

    @GetMapping("/enterprises/{enterpriseId}/pie-chart")
    public String getEnterprisePieChart(@PathVariable Long enterpriseId,
                                        @RequestParam(required = false) Integer year,
                                        Model model) {
        EnterprisePieChartDTO pieChartDTO = new EnterprisePieChartDTO();
        CommentDTO comment = new CommentDTO();
        String chartTitle = null;
        if (year != null) {
            List<ParameterDTO> enterpriseParameters = unitParameterService.getAllParametersByEnterpriseId(enterpriseId);
            for (ParameterDTO parameterDTO : enterpriseParameters) {
                String parameterName = parameterDTO.getBeanName();
                if (parameterRepository.existsByBeanNameAndHasTariff(parameterName, true)) {
                    List<YearInfo> tempYearInfos = parameterBaseService.getYearCostsDataForEnterpriseByParameterName(enterpriseId, parameterName, year);
                    pieChartDTO.addYearInfos(parameterService.getParameterByBeanName(parameterName).getCostsName(), tempYearInfos);
                }
            }
            comment = commentService.getCommentByDepartmentIdAndParameterName(enterpriseId, PIE_CHART + year);
            chartTitle = "Енергобаланс по підприємству \"" + unitService.getUnitById(enterpriseId).getName() + "\" за " + year + " рік";
        }
        model.addAttribute("pieChart", pieChartDTO);
        model.addAttribute("enterpriseId", enterpriseId);
        model.addAttribute("comment", comment);
        model.addAttribute("year", year);
        model.addAttribute("chartTitle", chartTitle);
        return "enterprise/pie-chart";
    }
}
