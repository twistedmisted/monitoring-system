package ua.kpi.mishchenko.monitoringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterWithTariff;
import ua.kpi.mishchenko.monitoringsystem.dto.TariffDTO;
import ua.kpi.mishchenko.monitoringsystem.service.ParameterService;
import ua.kpi.mishchenko.monitoringsystem.service.TariffService;

import java.util.List;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/tariffs")
@RequiredArgsConstructor
public class TariffController {

    private final ParameterService parameterService;
    private final TariffService tariffService;

    @GetMapping
    public String getTariffPage(@RequestParam(required = false) String name,
                                Model model) {
        if (!isNull(name)) {
            ParameterWithTariff parameterWithTariff = parameterService.getParameterWithTariffByName(name);
            if (isNull((parameterWithTariff.getTariff()))) {
                TariffDTO tariffDTO = new TariffDTO();
                tariffDTO.setId(parameterWithTariff.getParameter().getId());
                parameterWithTariff.setTariff(tariffDTO);
            }
            model.addAttribute("parameterWithTariff", parameterWithTariff);
        } else {
            model.addAttribute("parameterWithTariff", new ParameterWithTariff());
        }
        List<ParameterWithTariff> parametersWithTariff = parameterService.getAllParametersWithTariff();
        model.addAttribute("parametersWithTariff", parametersWithTariff);

        return "tariff";
    }

    @PostMapping
    public String getTariffPage(@RequestParam String name,
                                @ModelAttribute ParameterWithTariff parameterWithTariff) {
        tariffService.saveTariff(name, parameterWithTariff);
        return "redirect:/tariffs?name=" + name;
    }
}
