package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private Long id;
    private String text;
    private String parameterName;
    private Long unitId;
}
