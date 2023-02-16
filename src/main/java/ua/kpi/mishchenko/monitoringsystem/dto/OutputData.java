package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class OutputData {

    private List<YearValue> yearValues;
    private List<WorkingDaysDTO> workingDays;
    private String parameterName = "nothing";

    public void addYearValue(YearValue yearValue) {
        yearValues.add(yearValue);
    }
}
