package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateActivity {

    private String name;
    private String description;
    private String financialSource;
    private BigDecimal money;
    private Long parameterId;
    private Long departmentId;
}
