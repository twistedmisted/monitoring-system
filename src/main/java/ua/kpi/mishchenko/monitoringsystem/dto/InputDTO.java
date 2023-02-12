package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class InputDTO {

    private List<YearValue> yearValues;
    private String parameterName = "nothing";

    public void addYearValue(YearValue yearValue) {
        yearValues.add(yearValue);
    }
}
