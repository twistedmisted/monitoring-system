package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

@Data
public class InputYearInfo {

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

    public static Double toDoubleValue(String month) {
        return month.isBlank() ? 0.0 : Double.parseDouble(month);
    }

    public static String toStringValue(Double month) {
        return month == 0.0 ? "" : String.valueOf(month);
    }
}
