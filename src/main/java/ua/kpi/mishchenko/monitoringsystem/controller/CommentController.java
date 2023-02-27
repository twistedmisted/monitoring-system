package ua.kpi.mishchenko.monitoringsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kpi.mishchenko.monitoringsystem.dto.CommentDTO;
import ua.kpi.mishchenko.monitoringsystem.service.CommentService;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/consumption")
    public String createCommentForConsumptionChart(@RequestParam Long departmentId,
                                                   @RequestParam Long enterpriseId,
                                                   @RequestParam String parameterName,
                                                   @ModelAttribute CommentDTO consumptionComment) {
        consumptionComment.setUnitId(departmentId);
        consumptionComment.setParameterName(parameterName);
        commentService.createComment(consumptionComment);
        return "redirect:/units/enterprises/" + enterpriseId + "/departments/" + departmentId + "/line-chart?parameter-name=" + parameterName;
    }

    @PostMapping("/costs")
    public String createCommentForCostsChart(@RequestParam Long departmentId,
                                             @RequestParam Long enterpriseId,
                                             @RequestParam String parameterName,
                                             @ModelAttribute CommentDTO costsComment) {
        costsComment.setUnitId(departmentId);
        costsComment.setParameterName(parameterName  + "_costs");
        commentService.createComment(costsComment);
        return "redirect:/units/enterprises/" + enterpriseId + "/departments/" + departmentId + "/line-chart?parameter-name=" + parameterName;
    }

    @PostMapping("/consumption-enterprise")
    public String createCommentForConsumptionChart(@RequestParam Long enterpriseId,
                                                   @RequestParam String parameterName,
                                                   @ModelAttribute CommentDTO consumptionComment) {
        consumptionComment.setUnitId(enterpriseId);
        consumptionComment.setParameterName(parameterName);
        commentService.createComment(consumptionComment);
        return "redirect:/units/enterprises/" + enterpriseId + "/line-chart?parameter-name=" + parameterName;
    }

    @PostMapping("/costs-enterprise")
    public String createCommentForCostsChart(@RequestParam Long enterpriseId,
                                             @RequestParam String parameterName,
                                             @ModelAttribute CommentDTO costsComment) {
        costsComment.setUnitId(enterpriseId);
        costsComment.setParameterName(parameterName  + "_costs");
        commentService.createComment(costsComment);
        return "redirect:/units/enterprises/" + enterpriseId + "/line-chart?parameter-name=" + parameterName;
    }
}
