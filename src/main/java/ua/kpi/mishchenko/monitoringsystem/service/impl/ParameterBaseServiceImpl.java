package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.InputDataDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.InputYearInfo;
import ua.kpi.mishchenko.monitoringsystem.dto.MonthInfo;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterBaseDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterWithWorkingDays;
import ua.kpi.mishchenko.monitoringsystem.dto.TableData;
import ua.kpi.mishchenko.monitoringsystem.dto.YearInfo;
import ua.kpi.mishchenko.monitoringsystem.entity.ParameterBaseEntity;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitParameterEntity;
import ua.kpi.mishchenko.monitoringsystem.repository.UnitParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.service.ParameterBaseService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ParameterBaseServiceImpl implements ParameterBaseService {

    private static final String SELECT_VALUES_FOR_PARAMETER_BY_UNIT_ID = "SELECT * FROM %s WHERE unit_id = ? ORDER BY year, month";
    private static final String SELECT_VALUES_FOR_PARAMETER_BY_UNIT_ID_WITH_WORKINGS_DAYS = "SELECT parameter_table.year, parameter_table.month, parameter_table.value, wd.amount\n" +
            "FROM %s parameter_table\n" +
            "         LEFT JOIN working_days wd ON wd.month = parameter_table.month AND wd.year = parameter_table.year\n" +
            "WHERE parameter_table.unit_id = ?\n" +
            "ORDER BY year, month;";
    private final JdbcTemplate jdbcTemplate;
    private final UnitParameterRepository unitParameterRepository;

    @Override
    public InputDataDTO getDataByParameterName(Long unitId, String parameterName) {
        List<ParameterBaseEntity> values = getValuesForParameterByUnitId(unitId, parameterName);
        if (values.isEmpty()) {
            return createDefaultInputData();
        }
        List<InputYearInfo> yearInfos = new ArrayList<>();
        InputYearInfo yearInfo = new InputYearInfo();
        for (ParameterBaseEntity parameterBase : values) {
            if (isNotDefaultYear(yearInfo.getYear()) && yearsNotEquals(yearInfo.getYear(), parameterBase.getYear())) {
                yearInfos.add(yearInfo);
                yearInfo = new InputYearInfo();
            }
            yearInfo.setYear(String.valueOf(parameterBase.getYear()));
            mapValueByMonth(yearInfo, parameterBase.getMonth(), parameterBase.getValue());
        }
        yearInfos.add(yearInfo);
        UnitParameterEntity unitParameter = unitParameterRepository.findByUnitIdAndParameterBeanName(unitId, parameterName)
                .orElse(null);
        if (unitParameter != null && unitParameter.getAmountYear() != yearInfos.size()) {
            yearInfos.addAll(Collections.nCopies(unitParameter.getAmountYear() - yearInfos.size(), new InputYearInfo()));
        }
        InputDataDTO tableData = new InputDataDTO();
        tableData.setYearInfos(yearInfos);
        return tableData;
    }

    private InputDataDTO createDefaultInputData() {
        InputDataDTO tableData = new InputDataDTO();
        tableData.setYearInfos(new ArrayList<>(Collections.nCopies(10, new InputYearInfo())));
        return tableData;
    }

    private TableData createDefaultTableData() {
        TableData tableData = new TableData();
        tableData.setYearInfos(new ArrayList<>(Collections.nCopies(10, new YearInfo())));
        return tableData;
    }

    @Override
    public TableData getDataByParameterNameWithWorkingDays(Long unitId, String parameterName) {
        List<ParameterWithWorkingDays> values = getParameterValuesWithWorkingDays(unitId, parameterName);
        if (values.isEmpty()) {
            return createDefaultTableData();
        }
        List<YearInfo> yearInfos = new ArrayList<>();
        YearInfo yearInfo = new YearInfo();
        for (ParameterWithWorkingDays value : values) {
            if (isNotDefaultYear(yearInfo.getYear()) && yearsNotEquals(yearInfo.getYear(), value.getYear())) {
                yearInfos.add(yearInfo);
                yearInfo = new YearInfo();
            }
            yearInfo.setYear(String.valueOf(value.getYear()));
            mapValueByMonth(yearInfo, value);
        }
        yearInfos.add(yearInfo);
        TableData tableData = new TableData();
        tableData.setYearInfos(yearInfos);
        tableData.setParameterName(parameterName);
        return tableData;
    }

    private List<ParameterWithWorkingDays> getParameterValuesWithWorkingDays(Long unitId, String parameterName) {
        return jdbcTemplate.query(format(SELECT_VALUES_FOR_PARAMETER_BY_UNIT_ID_WITH_WORKINGS_DAYS, parameterName),
                (rs, rowNum) -> new ParameterWithWorkingDays(
                        rs.getInt("year"),
                        rs.getInt("month"),
                        rs.getDouble("value"),
                        rs.getInt("amount")),
                unitId);
    }

    private List<ParameterBaseEntity> getValuesForParameterByUnitId(Long unitId, String parameterName) {
        return jdbcTemplate.query(format(SELECT_VALUES_FOR_PARAMETER_BY_UNIT_ID, parameterName),
                (rs, rowNum) -> new ParameterBaseEntity(
                        rs.getLong("id"),
                        rs.getInt("year"),
                        rs.getInt("month"),
                        rs.getDouble("value")),
                unitId);
    }

    @Override
    public TableData getDataForEnterpriseByParameterName(Long unitId, String parameterName) {
        List<ParameterBaseDTO> values = jdbcTemplate.query("SELECT ROUND(SUM(parameter_table.value)::numeric, 2) AS value,\n" +
                        "       parameter_table.year,\n" +
                        "       parameter_table.month\n" +
                        "FROM " + parameterName + " parameter_table\n" +
                        "         INNER JOIN units u on u.id = parameter_table.unit_id\n" +
                        "WHERE u.parent_id = ?\n" +
                        "GROUP BY parameter_table.year, parameter_table.month\n" +
                        "ORDER BY parameter_table.year, parameter_table.month",
                (rs, rowNum) -> new ParameterBaseDTO(
                        rs.getInt("year"),
                        rs.getInt("month"),
                        rs.getDouble("value")),
                unitId);
        TableData tableData = new TableData();
        if (values.isEmpty()) {
            tableData.setYearInfos(new ArrayList<>(Collections.nCopies(10, new YearInfo())));
            return tableData;
        }
        return getTableData(parameterName, values);
    }

    private TableData getTableData(String parameterName, List<ParameterBaseDTO> values) {
        List<YearInfo> yearInfos = mapParameterDtosToYearValues(values);
        TableData tableData = new TableData();
        tableData.setYearInfos(yearInfos);
        tableData.setParameterName(parameterName);
        return tableData;
    }

    private List<YearInfo> mapParameterDtosToYearValues(List<ParameterBaseDTO> values) {
        List<YearInfo> yearInfos = new ArrayList<>();
        YearInfo yearInfo = new YearInfo();
        for (ParameterBaseDTO parameterBase : values) {
            if (isNotDefaultYear(yearInfo.getYear()) && yearsNotEquals(yearInfo.getYear(), parameterBase.getYear())) {
                yearInfos.add(yearInfo);
                yearInfo = new YearInfo();
            }
            yearInfo.setYear(String.valueOf(parameterBase.getYear()));
            mapValueByMonth(yearInfo, parameterBase.getMonth(), parameterBase.getValue());
        }
        yearInfos.add(yearInfo);
        return yearInfos;
    }

    private boolean yearsNotEquals(String firstYear, Integer secondYear) {
        return !firstYear.equals(String.valueOf(secondYear));
    }

    private boolean isNotDefaultYear(String year) {
        return !year.equals("xxxx");
    }

    private void mapValueByMonth(InputYearInfo yearInfo, int monthIndex, Double value) {
        switch (monthIndex) {
            case 1 -> yearInfo.setJanuary(InputYearInfo.toStringValue(value));
            case 2 -> yearInfo.setFebruary(InputYearInfo.toStringValue(value));
            case 3 -> yearInfo.setMarch(InputYearInfo.toStringValue(value));
            case 4 -> yearInfo.setApril(InputYearInfo.toStringValue(value));
            case 5 -> yearInfo.setMay(InputYearInfo.toStringValue(value));
            case 6 -> yearInfo.setJune(InputYearInfo.toStringValue(value));
            case 7 -> yearInfo.setJuly(InputYearInfo.toStringValue(value));
            case 8 -> yearInfo.setAugust(InputYearInfo.toStringValue(value));
            case 9 -> yearInfo.setSeptember(InputYearInfo.toStringValue(value));
            case 10 -> yearInfo.setOctober(InputYearInfo.toStringValue(value));
            case 11 -> yearInfo.setNovember(InputYearInfo.toStringValue(value));
            case 12 -> yearInfo.setDecember(InputYearInfo.toStringValue(value));
        }
    }

    private void mapValueByMonth(YearInfo yearInfo, int monthIndex, Double value) {
        switch (monthIndex) {
            case 1 -> yearInfo.addMonthByMonth("january", new MonthInfo(monthIndex, value));
            case 2 -> yearInfo.addMonthByMonth("february", new MonthInfo(monthIndex, value));
            case 3 -> yearInfo.addMonthByMonth("march", new MonthInfo(monthIndex, value));
            case 4 -> yearInfo.addMonthByMonth("april", new MonthInfo(monthIndex, value));
            case 5 -> yearInfo.addMonthByMonth("may", new MonthInfo(monthIndex, value));
            case 6 -> yearInfo.addMonthByMonth("june", new MonthInfo(monthIndex, value));
            case 7 -> yearInfo.addMonthByMonth("july", new MonthInfo(monthIndex, value));
            case 8 -> yearInfo.addMonthByMonth("august", new MonthInfo(monthIndex, value));
            case 9 -> yearInfo.addMonthByMonth("september", new MonthInfo(monthIndex, value));
            case 10 -> yearInfo.addMonthByMonth("october", new MonthInfo(monthIndex, value));
            case 11 -> yearInfo.addMonthByMonth("november", new MonthInfo(monthIndex, value));
            case 12 -> yearInfo.addMonthByMonth("december", new MonthInfo(monthIndex, value));
        }
    }

    private void mapValueByMonth(YearInfo yearInfo, ParameterWithWorkingDays value) {
//        final String monthValueStr = YearInfo.toStringValue(value.getValue());
//        String amountWorkingDaysStr = getAmountWorkingDaysStr(value.getAmount(), monthValueStr);
        double monthDailyAverage = getMonthDailyAverage(value.getValue(), value.getAmount());
//        String monthDailyAverageStr = DECIMAL_FORMAT.format(monthDailyAverage);
        switch (value.getMonth()) {
            case 1 -> {
                yearInfo.addMonthByMonth("january", new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
//                yearInfo.setJanuary(monthValueStr);
//                yearInfo.setJanuaryAmount(amountWorkingDaysStr);
//                yearInfo.setJanuaryDailyAverage(monthDailyAverageStr);
            }
            case 2 -> {
                yearInfo.addMonthByMonth("february", new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 3 -> {
                yearInfo.addMonthByMonth("march", new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 4 -> {
                yearInfo.addMonthByMonth("april", new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 5 -> {
                yearInfo.addMonthByMonth("may", new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 6 -> {
                yearInfo.addMonthByMonth("june", new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 7 -> {
                yearInfo.addMonthByMonth("july", new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 8 -> {
                yearInfo.addMonthByMonth("august", new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 9 -> {
                yearInfo.addMonthByMonth("september", new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 10 -> {
                yearInfo.addMonthByMonth("october", new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 11 -> {
                yearInfo.addMonthByMonth("november", new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 12 -> {
                yearInfo.addMonthByMonth("december", new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
        }
    }

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private Double getMonthDailyAverage(Double value, Integer amount) {
        if (value > 0 && amount > 0) {
            return value / amount;
        }
        return 0.0;
    }

    private String getAmountWorkingDaysStr(Integer workingDays, String monthValueStr) {
        String amountWorkingDaysStr = "";
        if (!monthValueStr.isBlank()) {
            amountWorkingDaysStr = YearInfo.toStringValue(workingDays);
        }
        return amountWorkingDaysStr;
    }

    @Override
    public TableData getDataForEnterpriseByParameterNameAndYear(Long unitId, String parameterName, Integer year) {
        List<ParameterBaseDTO> values = jdbcTemplate.query("SELECT ROUND(SUM(parameter_table.value)::numeric, 2) AS value,\n" +
                        "       parameter_table.year,\n" +
                        "       parameter_table.month\n" +
                        "FROM " + parameterName + " parameter_table\n" +
                        "         INNER JOIN units u on u.id = parameter_table.unit_id\n" +
                        "WHERE u.parent_id = ? AND parameter_table.year = ?\n" +
                        "GROUP BY parameter_table.year, parameter_table.month;",
                (rs, rowNum) -> new ParameterBaseDTO(
                        rs.getInt("year"),
                        rs.getInt("month"),
                        rs.getDouble("value")),
                unitId, year);
        if (values.isEmpty()) {
            TableData tableData = new TableData();
            tableData.setYearInfos(new ArrayList<>(Collections.nCopies(1, new YearInfo())));
            return tableData;
        }
        return getTableData(parameterName, values);
    }

    @Override
    public void saveData(Long unitId, InputDataDTO tableData) {
        String parameterName = tableData.getParameterName();
        jdbcTemplate.update("DELETE FROM " + parameterName + " WHERE unit_id = ?", unitId);
        for (InputYearInfo yearInfo : tableData.getYearInfos()) {
            save(unitId, parameterName, Integer.parseInt(yearInfo.getYear()), 1, InputYearInfo.toDoubleValue(yearInfo.getJanuary()));
            save(unitId, parameterName, Integer.parseInt(yearInfo.getYear()), 2, InputYearInfo.toDoubleValue(yearInfo.getFebruary()));
            save(unitId, parameterName, Integer.parseInt(yearInfo.getYear()), 3, InputYearInfo.toDoubleValue(yearInfo.getMarch()));
            save(unitId, parameterName, Integer.parseInt(yearInfo.getYear()), 4, InputYearInfo.toDoubleValue(yearInfo.getApril()));
            save(unitId, parameterName, Integer.parseInt(yearInfo.getYear()), 5, InputYearInfo.toDoubleValue(yearInfo.getMay()));
            save(unitId, parameterName, Integer.parseInt(yearInfo.getYear()), 6, InputYearInfo.toDoubleValue(yearInfo.getJune()));
            save(unitId, parameterName, Integer.parseInt(yearInfo.getYear()), 7, InputYearInfo.toDoubleValue(yearInfo.getJuly()));
            save(unitId, parameterName, Integer.parseInt(yearInfo.getYear()), 8, InputYearInfo.toDoubleValue(yearInfo.getAugust()));
            save(unitId, parameterName, Integer.parseInt(yearInfo.getYear()), 9, InputYearInfo.toDoubleValue(yearInfo.getSeptember()));
            save(unitId, parameterName, Integer.parseInt(yearInfo.getYear()), 10, InputYearInfo.toDoubleValue(yearInfo.getOctober()));
            save(unitId, parameterName, Integer.parseInt(yearInfo.getYear()), 11, InputYearInfo.toDoubleValue(yearInfo.getNovember()));
            save(unitId, parameterName, Integer.parseInt(yearInfo.getYear()), 12, InputYearInfo.toDoubleValue(yearInfo.getDecember()));
        }
    }

    private void save(Long unitId, String tableName, Integer year, Integer month, Double value) {
        jdbcTemplate.update("INSERT INTO " + tableName + " (year, month, value, unit_id) VALUES (?, ?, ?, ?)",
                year,
                month,
                value,
                unitId);
    }
}
