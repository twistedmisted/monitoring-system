package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterWithWorkingDays {

    private Integer year;
    private Integer month;
    private Double value;
    private Integer amount;
}
