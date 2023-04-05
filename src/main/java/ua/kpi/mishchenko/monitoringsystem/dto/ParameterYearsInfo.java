package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Objects.isNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterYearsInfo {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private Map<Integer, List<YearInfo>> yearsInfo = new TreeMap<>();
    private String parameterName = "nothing";
    private Map<Integer, Integer> minByYear = new HashMap<>();
    private Map<Integer, Integer> maxByYear = new HashMap<>();

    public String yearAverage(Integer year) {
        Double aver = calcYearAverage(year);
        return aver == 0 ? "" : DECIMAL_FORMAT.format(aver);
    }

    public String average() {
        Double aver = calcAverage();
        return aver == 0 ? "" : DECIMAL_FORMAT.format(aver);
    }

    public Double calcAverage() {
        double sum = 0;
        int amount = 0;
        for (Integer year : yearsInfo.keySet()) {
            Double yearAverage = calcYearAverage(year);
            if (yearAverage > 0) {
                sum += yearAverage;
                amount++;
            }
        }
        return sum == 0 ? 0.0 : sum / amount;
    }

    public Double calcYearAverage(Integer year) {
        List<YearInfo> yearsInfo = this.yearsInfo.get(year);
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

    public String monthAverage(Integer year, Integer month) {
        Double aver = calcMonthAverage(year, month);
        return aver == 0 ? "" : DECIMAL_FORMAT.format(aver);
    }

    public Double calcMonthAverage(Integer year, Integer month) {
        List<YearInfo> yearsInfo = this.yearsInfo.get(year);
        if (isNull(yearsInfo) || yearsInfo.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        int amount = 0;
        for (YearInfo yearInfo : yearsInfo) {
            Double dailyAverage = yearInfo.getMonthInfo(month).getDailyAverage();
            if (dailyAverage > 0) {
                sum += dailyAverage;
                amount++;
            }
        }
        if (sum == 0) {
            return 0.0;
        }
        return sum / amount;
    }

    public String monthValue(Integer year, Integer month) {
        Double value = calcMonthValue(year, month);
        return value == 0 ? "" : DECIMAL_FORMAT.format(value);
    }

    public Double calcMonthValue(Integer year, Integer month) {
        List<YearInfo> yearsInfo = this.yearsInfo.get(year);
        if (isNull(yearsInfo) || yearsInfo.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (YearInfo yearInfo : yearsInfo) {
            sum += yearInfo.getMonthInfo(month).getValue();
        }
        return sum;
    }

    public String getMinValue(Integer year) {
        if (!isNull(minByYear.get(year))) {
            return monthAverage(year, minByYear.get(year));
        }
        double min = getMin(year);
        return min == 0 ? "" : DECIMAL_FORMAT.format(min);
    }

    private double getMin(Integer year) {
        double min = calcMonthAverage(year, 1);
        if (min > 0) {
            minByYear.put(year, 1);
        }
        for (int i = 2; i < 13; i++) {
            double tempMin = calcMonthAverage(year, i);
            if (min > tempMin && tempMin > 0 || min == 0) {
                min = tempMin;
                minByYear.put(year, i);
            }
        }
        return min;
    }

    public String getMinMonth(Integer year) {
        if (!isNull(minByYear.get(year))) {
            return String.valueOf(minByYear.get(year));
        }
        getMin(year);
        return minByYear.get(year) == null ? "" : String.valueOf(minByYear.get(year));
    }

    public String getMaxValue(Integer year) {
        if (!isNull(maxByYear.get(year))) {
            return monthAverage(year, maxByYear.get(year));
        }
        double max = getMax(year);
        return max == 0 ? "" : DECIMAL_FORMAT.format(max);
    }

    private double getMax(Integer year) {
        double max = calcMonthAverage(year, 1);
        if (max > 0) {
            maxByYear.put(year, 1);
        }
        for (int i = 2; i < 13; i++) {
            double tempMin = calcMonthAverage(year, i);
            if (max < tempMin) {
                max = tempMin;
                maxByYear.put(year, i);
            }
        }
        return max;
    }

    public String getMaxMonth(Integer year) {
        if (!isNull(maxByYear.get(year))) {
            return String.valueOf(maxByYear.get(year));
        }
        getMax(year);
        return maxByYear.get(year) == null ? "" : String.valueOf(maxByYear.get(year));
    }

    public String calcInequalityCoefficient(Integer year) {
        double max = getMax(year);
        double min = getMin(year);
        if (min == 0 || max == 0) {
            return "";
        }
        return DECIMAL_FORMAT.format(max / min);
    }

    public void addYearInfo(Integer year, YearInfo yearInfo) {
        List<YearInfo> value = yearsInfo.get(year);
        if (isNull(value)) {
            yearsInfo.put(year, new ArrayList<>(List.of(yearInfo)));
            return;
        }
        value.add(yearInfo);
    }
}
