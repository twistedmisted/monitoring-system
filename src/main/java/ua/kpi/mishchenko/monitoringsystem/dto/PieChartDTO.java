package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PieChartDTO {

    private Map<String, YearInfo> costs = new HashMap<>();

    public void addYearInfo(String key, YearInfo value) {
        costs.put(key, value);
    }

    public YearInfo getYearInfo(String key) {
        return costs.get(key);
    }
}
