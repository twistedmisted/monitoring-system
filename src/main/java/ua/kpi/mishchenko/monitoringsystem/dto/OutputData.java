package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class OutputData {

    private List<YearInfo> yearInfos;
    private List<WorkingDaysDTO> workingDays;
    private String parameterName = "nothing";

    public void addYearValue(YearInfo yearInfo) {
        yearInfos.add(yearInfo);
    }
}
