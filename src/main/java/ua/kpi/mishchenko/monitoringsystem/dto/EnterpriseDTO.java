package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EnterpriseDTO {

    private Long id;
    private String name;
    private List<SectionDTO> departments = new ArrayList<>();
}
