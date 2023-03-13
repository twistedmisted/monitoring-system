package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableData {

    private List<YearInfo> yearInfos = new ArrayList<>();
    private String parameterName = "nothing";

    public void addYearValue(YearInfo yearInfo) {
        yearInfos.add(yearInfo);
    }
}
