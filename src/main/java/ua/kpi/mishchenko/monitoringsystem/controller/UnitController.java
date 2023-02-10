package ua.kpi.mishchenko.monitoringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kpi.mishchenko.monitoringsystem.dto.EnterpriseDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.SetParametersRequest;
import ua.kpi.mishchenko.monitoringsystem.dto.UnitDTO;
import ua.kpi.mishchenko.monitoringsystem.service.UnitParameterService;
import ua.kpi.mishchenko.monitoringsystem.service.UnitService;

import java.util.List;

@Controller
@RequestMapping("/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;
    private final UnitParameterService unitParameterService;

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
        if (department == null) {
            model.addAttribute("message", "Відділу не знайдено!");
        } else {
            model.addAttribute("department", department);
            model.addAttribute("parameters", parameters);
            model.addAttribute("enterpriseId", enterpriseId);

            // TODO: SetParametersRequest need to create
            model.addAttribute("setParameters", new SetParametersRequest());
        }
        return "parameters";
    }

    @PostMapping("/enterprises/{enterpriseId}/departments/{departmentId}/parameters")
    public String setDepartmentParameters(@PathVariable Long enterpriseId,
                                          @PathVariable Long departmentId,
                                          @ModelAttribute SetParametersRequest setParameters) {
        unitService.setDepartmentParameters(departmentId, setParameters);
        return "redirect:/units";
    }
}
