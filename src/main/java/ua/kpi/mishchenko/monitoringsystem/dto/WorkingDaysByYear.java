package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkingDaysByYear {

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

    public WorkingDaysByYear(Integer year) {
        this.year = year;
    }
}
