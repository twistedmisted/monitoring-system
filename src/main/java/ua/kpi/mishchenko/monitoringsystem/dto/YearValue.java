package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.text.DecimalFormat;

@Data
public class YearValue {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private String year = "xxxx";
    private Double january = 0.0;
    private Double february = 0.0;
    private Double march = 0.0;
    private Double april = 0.0;
    private Double may = 0.0;
    private Double june = 0.0;
    private Double july = 0.0;
    private Double august = 0.0;
    private Double september = 0.0;
    private Double october = 0.0;
    private Double november = 0.0;
    private Double december = 0.0;

    public String getTotalValue() {
        return DECIMAL_FORMAT.format(january + february + march + april + may + june +
                july + august + september + october + november + december);
    }
}
