package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;

@Data
public class YearValue {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private String year = "xxxx";
    private String january = "";
    private String february = "";
    private String march = "";
    private String april = "";
    private String may = "";
    private String june = "";
    private String july = "";
    private String august = "";
    private String september = "";
    private String october = "";
    private String november = "";
    private String december = "";

    public String getTotalValue() {
        return DECIMAL_FORMAT.format(parseDouble(january) +
                parseDouble(february) +
                parseDouble(february) +
                parseDouble(april) +
                parseDouble(may) +
                parseDouble(june) +
                parseDouble(july) +
                parseDouble(august) +
                parseDouble(september) +
                parseDouble(october) +
                parseDouble(november) +
                parseDouble(december));
    }

    public static Double toDoubleValue(String month) {
        return month.isBlank() ? 0.0 : Double.parseDouble(month);
    }

    public static String toStringValue(Double month) {
        return month == 0.0 ? "" : String.valueOf(month);
    }
}
