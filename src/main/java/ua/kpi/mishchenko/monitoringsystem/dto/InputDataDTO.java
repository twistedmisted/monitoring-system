package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class InputDataDTO {

    private List<InputYearInfo> yearInfos;
    private String parameterName = "nothing";

    public void addYearValue(InputYearInfo yearInfo) {
        yearInfos.add(yearInfo);
    }
}
