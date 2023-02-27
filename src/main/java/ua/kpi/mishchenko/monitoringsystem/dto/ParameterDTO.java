package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

@Data
public class ParameterDTO {

    private Long id;
    private String name;
    private String beanName;
    private Boolean hasTariff;
    private String costsName;
}
