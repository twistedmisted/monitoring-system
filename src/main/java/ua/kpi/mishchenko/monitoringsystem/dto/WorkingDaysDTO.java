package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingDaysDTO {

    private Long id;
    private Long sectionId;
    private Integer year;
    private Integer month;
    private Integer amount;
}
