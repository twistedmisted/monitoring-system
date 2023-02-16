package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingDaysDTO {

    private Long id;
    private Long unitId;
    private Integer year;
    private Integer january;
    private Integer february;
    private Integer march;
    private Integer april;
    private Integer may;
    private Integer june;
    private Integer july;
    private Integer august;
    private Integer september;
    private Integer october;
    private Integer november;
    private Integer december;
}
