package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static java.util.Objects.isNull;

@Data
public class EfficiencyBO {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private String parameterName;
    private Double cons;
    private Double costs;
    private BigDecimal money;

    public String cons() {
        if (isNull(cons)) {
            return "";
        }
        return cons == 0 ? "" : DECIMAL_FORMAT.format(cons);
    }

    public String costs() {
        if (isNull(costs)) {
            return "";
        }
        return costs == 0 ? "" : DECIMAL_FORMAT.format(costs);
    }
}
