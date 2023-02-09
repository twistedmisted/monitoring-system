package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UnitDTO {

    private Long id;
    private Long parentId;
    private String name;
    private List<ParameterDTO> parameters = new ArrayList<>();
}
