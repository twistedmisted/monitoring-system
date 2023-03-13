package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Data
public class EnterprisePieChartDTO {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private Map<String, List<YearInfo>> costs = new HashMap<>();

    public String yearAverage(String key) {
        Double aver = calcYearAverage(key);
        return aver == 0 ? "" : DECIMAL_FORMAT.format(aver);
    }

    public Double calcYearAverage(String key) {
        List<YearInfo> yearsInfo = this.costs.get(key);
        if (isNull(yearsInfo) || yearsInfo.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        int amount = 0;
        for (YearInfo yearInfo : yearsInfo) {
            Double tempAver = yearInfo.calcYearAverage();
            if (tempAver > 0) {
                sum += tempAver;
                amount++;
            }
        }
        if (sum == 0) {
            return 0.0;
        }
        return sum / amount;
    }

    public void addYearInfos(String key, List<YearInfo> yearInfos) {
        costs.put(key, yearInfos);
    }
}
