package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Data
public class TableData {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private List<YearInfo> yearInfos = new ArrayList<>();
    private String parameterName = "nothing";

    public void addYearValue(YearInfo yearInfo) {
        yearInfos.add(yearInfo);
    }

    public String yearAverage() {
        Double aver = average();
        return aver == 0 ? "" : DECIMAL_FORMAT.format(aver);
    }

    public Double average() {
        if (yearInfos.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        int amount = 0;
        for (YearInfo yearInfo : yearInfos) {
            Double yearAverage = yearInfo.calcYearAverage();
            if (yearAverage > 0) {
                sum += yearAverage;
                amount++;
            }
        }
        return sum == 0 ? 0.0 : sum / amount;
    }
}
