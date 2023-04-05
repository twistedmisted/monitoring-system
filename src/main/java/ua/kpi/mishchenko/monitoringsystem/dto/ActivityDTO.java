package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActivityDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal money;
    private String financialSource;
    private ParameterDTO parameter;
    private SectionDTO section;
}
