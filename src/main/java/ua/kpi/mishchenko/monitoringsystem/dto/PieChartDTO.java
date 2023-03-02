package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PieChartDTO {

    private Map<String, YearInfo> costs = new HashMap<>();

    public String sum() {
        return String.valueOf(costs.values()
                .stream()
                .map(YearInfo::calcYearAverage)
                .reduce(Double::sum)
        );
    }

    public void addYearInfo(String key, YearInfo value) {
        costs.put(key, value);
    }

    public YearInfo getYearInfo(String key) {
        return costs.get(key);
    }
}
