package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthInfo {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private Integer month;
    private Double value = 0.0;
    private Integer workingDays = 0;
    private Double dailyAverage = 0.0;

    public String valueToStr() {
        return value == 0.0 || workingDays == 0 ? "" : DECIMAL_FORMAT.format(value);
    }

    public String workingDaysToStr() {
        return value == 0.0 || workingDays == 0 ? "" : String.valueOf(workingDays);
    }

    public String dailyAverageToString() {
        return dailyAverage == 0.0 ? "" : DECIMAL_FORMAT.format(dailyAverage);
    }

    public MonthInfo(Integer month, Double value) {
        this.month = month;
        this.value = value;
    }
}
