package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SectionDTO {

    private Long id;
    private Long parentId;
    private String name;
    private List<ParameterDTO> parameters = new ArrayList<>();
    private List<WorkingDaysDTO> workingDays = new ArrayList<>();
}
