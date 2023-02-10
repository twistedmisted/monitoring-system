package ua.kpi.mishchenko.monitoringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kpi.mishchenko.monitoringsystem.dto.EnterpriseDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.UnitDTO;
import ua.kpi.mishchenko.monitoringsystem.service.UnitService;

import java.util.List;

@Controller
@RequestMapping("/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

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
        // TODO: check if it's enterprise
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
}
