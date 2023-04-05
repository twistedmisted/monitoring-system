package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityBO {

    private String departmentName;
    private String name;
    private String description;
    private BigDecimal money;
    private String parameterName;
    private String financialSource;
}
