package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParameterBaseDTO {

    private Long id;
    private Integer year;
    private Integer month;
    private Double value;

    public ParameterBaseDTO(Integer year, Integer month, Double value) {
        this.year = year;
        this.month = month;
        this.value = value;
    }
}
