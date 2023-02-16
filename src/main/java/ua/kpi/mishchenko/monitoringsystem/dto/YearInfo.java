package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Data
public class YearInfo {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private String year = "xxxx";
    private String minMonthKey = null;
    private String maxMonthKey = null;
    private Map<String, MonthInfo> months = new HashMap<>();

    public YearInfo() {
        months.put("january", new MonthInfo());
        months.put("february", new MonthInfo());
        months.put("march", new MonthInfo());
        months.put("april", new MonthInfo());
        months.put("may", new MonthInfo());
        months.put("june", new MonthInfo());
        months.put("july", new MonthInfo());
        months.put("august", new MonthInfo());
        months.put("september", new MonthInfo());
        months.put("october", new MonthInfo());
        months.put("november", new MonthInfo());
        months.put("december", new MonthInfo());
    }

    public String getTotalValue() {
        return DECIMAL_FORMAT.format(months.values()
                .stream()
                .map(MonthInfo::getValue)
                .reduce(Double::sum)
                .orElse(0.0));
    }

    public String calcYearAverage() {
        Collection<MonthInfo> values = months.values();
        double sum = 0.0;
        int amount = 0;
        for (MonthInfo value : values) {
            if (value.getDailyAverage() > 0) {
                sum += value.getDailyAverage();
                amount++;
            }
        }
        if (sum == 0.0) {
            return "";
        }
        return DECIMAL_FORMAT.format(sum / amount);
    }

    public String getMinValue() {
        if (months.isEmpty()) {
            return "";
        }
        if (!isNull(minMonthKey)) {
            return months.get(minMonthKey).dailyAverageToString();
        }
        double min = getMin();
        return min == 0 ? "" : DECIMAL_FORMAT.format(min);
    }

    public String getMinMonth() {
        if (months.isEmpty()) {
            return "";
        }
        if (!isNull(minMonthKey)) {
            return String.valueOf(months.get(minMonthKey).getMonth());
        }
        double min = getMin();
        if (min == 0) {
            return "";
        }
        return String.valueOf(months.get(minMonthKey).getMonth());
    }

    private double getMin() {
        Map.Entry<String, MonthInfo> entry = findFirstBigThanZero();
        if (isNull(entry)) {
            return 0;
        }
        minMonthKey = entry.getKey();
        final double[] min = {months.get(minMonthKey).getDailyAverage()};
        months.forEach((key, value) -> {
            if (min[0] > value.getDailyAverage() && value.getDailyAverage() > 0) {
                min[0] = value.getDailyAverage();
                minMonthKey = key;
            }
        });
        return min[0];
    }

    public String getMaxValue() {
        if (months.isEmpty()) {
            return "";
        }
        if (!isNull(maxMonthKey)) {
            return months.get(maxMonthKey).dailyAverageToString();
        }
        double max = getMax();
        return max == 0 ? "" : DECIMAL_FORMAT.format(max);
    }

    public String getMaxMonth() {
        if (months.isEmpty()) {
            return "";
        }
        if (!isNull(maxMonthKey)) {
            return String.valueOf(months.get(maxMonthKey).getMonth());
        }
        double max = getMax();
        if (max == 0) {
            return "";
        }
        return String.valueOf(months.get(maxMonthKey).getMonth());
    }

    private double getMax() {
        Map.Entry<String, MonthInfo> entry = findFirstBigThanZero();
        if (isNull(entry)) {
            return 0;
        }
        maxMonthKey = entry.getKey();
        final double[] max = {months.get(maxMonthKey).getDailyAverage()};
        months.forEach((key, value) -> {
            if (max[0] < value.getDailyAverage()) {
                max[0] = value.getDailyAverage();
                maxMonthKey = key;
            }
        });
        return max[0];
    }

    private Map.Entry<String, MonthInfo> findFirstBigThanZero() {
        return months.entrySet()
                .stream()
                .filter(e -> e.getValue().getDailyAverage() > 0)
                .findFirst()
                .orElse(null);
    }

    public String calcInequalityCoefficient() {
        double max = getMax();
        double min = getMin();
        if (min == 0 || max == 0) {
            return "";
        }
        return DECIMAL_FORMAT.format(max / min);
    }

    public static Double toDoubleValue(String month) {
        return month.isBlank() ? 0.0 : Double.parseDouble(month);
    }

    public static String toStringValue(Double month) {
        return month == 0.0 ? "" : String.valueOf(month);
    }

    public static String toStringValue(Integer workingDays) {
        return workingDays == 0 ? "" : String.valueOf(workingDays);
    }

    public void addMonthByMonth(String month, MonthInfo value) {
        months.put(month, value);
    }
}
