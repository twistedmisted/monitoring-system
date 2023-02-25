package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.InputDataDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.InputYearInfo;
import ua.kpi.mishchenko.monitoringsystem.dto.MonthInfo;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterBaseDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterWithWorkingDays;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterYearsInfo;
import ua.kpi.mishchenko.monitoringsystem.dto.TableData;
import ua.kpi.mishchenko.monitoringsystem.dto.YearInfo;
import ua.kpi.mishchenko.monitoringsystem.entity.ParameterBaseEntity;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitParameterEntity;
import ua.kpi.mishchenko.monitoringsystem.repository.UnitParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.service.ParameterBaseService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ParameterBaseServiceImpl implements ParameterBaseService {

    private static final String SELECT_VALUES_FOR_PARAMETER_BY_UNIT_ID = "SELECT * FROM %s WHERE unit_id = ? ORDER BY year, month";
    private static final String SELECT_VALUES_FOR_PARAMETER_BY_UNIT_ID_WITH_WORKINGS_DAYS = """
            SELECT parameter_table.year, parameter_table.month, parameter_table.value, wd.amount
            FROM %s parameter_table
                     LEFT JOIN working_days wd ON wd.unit_id = parameter_table.unit_id AND wd.month = parameter_table.month AND wd.year = parameter_table.year
            WHERE parameter_table.unit_id = ?
            ORDER BY year, month;""";
    private static final String SELECT_COSTS_VALUES_FOR_PARAMETER_BY_UNIT_ID_WITH_WORKINGS_DAYS = """
            SELECT parameter_table.year,
                   parameter_table.month,
                   ROUND((t.price * parameter_table.value)::numeric, 2) AS value,
                   wd.amount
            FROM tariffs t
                     INNER JOIN %s parameter_table ON parameter_table.unit_id = %s
                     INNER JOIN parameters p on p.bean_name = '%s'
                     LEFT JOIN working_days wd ON wd.unit_id = parameter_table.unit_id AND wd.month = parameter_table.month AND
                                                  wd.year = parameter_table.year
            WHERE parameter_table.unit_id = ?
              AND t.parameter_id = p.id
            ORDER BY year, month;""";
    private static final String SELECT_VALUES_FOR_PARAMETER_BY_ENTERPRISE_ID_WITH_WORKING_DAYS = """
            SELECT parameter_table.value,
                   parameter_table.year,
                   parameter_table.month,
                   parameter_table.amount
            FROM %s parameter_table
                     INNER JOIN units u ON u.id = parameter_table.unit_id
                     INNER JOIN working_days wd ON parameter_table.unit_id = wd.unit_id AND parameter_table.year = wd.year AND
                                                            parameter_table.month = wd.month
            WHERE u.parent_id = ?
            ORDER BY parameter_table.year, parameter_table.month;""";

    private static final String SELECT_DEPARTMENTS_ID_BY_ENTERPRISE_ID_AND_PARAMETER_BEAN_NAME = """
            SELECT up.unit_id AS id
            FROM units_parameters up
                     INNER JOIN parameters p ON up.parameter_id = p.id
                     INNER JOIN units u on u.id = up.unit_id
            WHERE p.bean_name = ?
              AND u.parent_id = ?;""";

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
        List<YearInfo> yearInfos = getYearInfosByDepartmentIdAndParameterBeanName(unitId, parameterName);
        if (yearInfos.isEmpty()) {
            return createDefaultTableData();
        }
        TableData tableData = new TableData();
        tableData.setYearInfos(yearInfos);
        tableData.setParameterName(parameterName);
        return tableData;
    }

    private List<YearInfo> getYearInfosByDepartmentIdAndParameterBeanName(Long departmentId, String beanName) {
        List<ParameterWithWorkingDays> values = getParameterValuesWithWorkingDays(departmentId, beanName);
        return getYearInfos(values);
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

    @Override
    public TableData getCostsDataByParameterNameWithWorkingDays(Long unitId, String parameterName) {
        List<YearInfo> yearInfos = getCostsYearInfosByDepartmentIdAndParameterBeanName(unitId, parameterName);
        if (yearInfos.isEmpty()) {
            return createDefaultTableData();
        }
        TableData tableData = new TableData();
        tableData.setYearInfos(yearInfos);
        tableData.setParameterName(parameterName);
        return tableData;
    }

    private List<YearInfo> getCostsYearInfosByDepartmentIdAndParameterBeanName(Long departmentId, String beanName) {
        List<ParameterWithWorkingDays> values = getCostsParameterValuesWithWorkingDays(departmentId, beanName);
        return getYearInfos(values);
    }

    private List<YearInfo> getYearInfos(List<ParameterWithWorkingDays> values) {
        if (values.isEmpty()) {
            return new ArrayList<>();
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
        return yearInfos;
    }

    private List<ParameterWithWorkingDays> getCostsParameterValuesWithWorkingDays(Long unitId, String parameterName) {
        return jdbcTemplate.query(format(SELECT_COSTS_VALUES_FOR_PARAMETER_BY_UNIT_ID_WITH_WORKINGS_DAYS, parameterName, unitId, parameterName),
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
    public ParameterYearsInfo getDataForEnterpriseByParameterName(Long unitId, String parameterName) {
        ParameterYearsInfo parameterYearsInfo = new ParameterYearsInfo();
        parameterYearsInfo.setParameterName(parameterName);
        List<Long> departmentsId = getDepartmentsIdByEnterpriseIdAndParameterBeanName(unitId, parameterName);
        for (Long departmentId : departmentsId) {
            List<YearInfo> yearInfoByDepartment = getYearInfosByDepartmentIdAndParameterBeanName(departmentId, parameterName);
            for (YearInfo yearInfo : yearInfoByDepartment) {
                parameterYearsInfo.addYearInfo(Integer.valueOf(yearInfo.getYear()), yearInfo);
            }
        }
        return parameterYearsInfo;
    }

    @Override
    public ParameterYearsInfo getCostsDataForEnterpriseByParameterName(Long unitId, String parameterName) {
        ParameterYearsInfo parameterYearsInfo = new ParameterYearsInfo();
        parameterYearsInfo.setParameterName(parameterName);
        List<Long> departmentsId = getDepartmentsIdByEnterpriseIdAndParameterBeanName(unitId, parameterName);
        for (Long departmentId : departmentsId) {
            List<YearInfo> yearInfoByDepartment = getCostsYearInfosByDepartmentIdAndParameterBeanName(departmentId, parameterName);
            for (YearInfo yearInfo : yearInfoByDepartment) {
                parameterYearsInfo.addYearInfo(Integer.valueOf(yearInfo.getYear()), yearInfo);
            }
        }
        return parameterYearsInfo;
    }

    private List<Long> getDepartmentsIdByEnterpriseIdAndParameterBeanName(Long unitId, String beanName) {
        return jdbcTemplate.query(SELECT_DEPARTMENTS_ID_BY_ENTERPRISE_ID_AND_PARAMETER_BEAN_NAME,
                (rs, rowNum) -> rs.getLong("id"),
                beanName, unitId);
    }

    private List<ParameterWithWorkingDays> getEnterpriseParameterValuesWithWorkingDays(Long unitId, String parameterName) {
        return jdbcTemplate.query(format(SELECT_VALUES_FOR_PARAMETER_BY_ENTERPRISE_ID_WITH_WORKING_DAYS, parameterName),
                (rs, rowNum) -> new ParameterWithWorkingDays(
                        rs.getInt("year"),
                        rs.getInt("month"),
                        rs.getDouble("value"),
                        rs.getInt("amount")),
                unitId);
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
            case 1 -> yearInfo.addMonthByMonth(1, new MonthInfo(monthIndex, value));
            case 2 -> yearInfo.addMonthByMonth(2, new MonthInfo(monthIndex, value));
            case 3 -> yearInfo.addMonthByMonth(3, new MonthInfo(monthIndex, value));
            case 4 -> yearInfo.addMonthByMonth(4, new MonthInfo(monthIndex, value));
            case 5 -> yearInfo.addMonthByMonth(5, new MonthInfo(monthIndex, value));
            case 6 -> yearInfo.addMonthByMonth(6, new MonthInfo(monthIndex, value));
            case 7 -> yearInfo.addMonthByMonth(7, new MonthInfo(monthIndex, value));
            case 8 -> yearInfo.addMonthByMonth(8, new MonthInfo(monthIndex, value));
            case 9 -> yearInfo.addMonthByMonth(9, new MonthInfo(monthIndex, value));
            case 10 -> yearInfo.addMonthByMonth(10, new MonthInfo(monthIndex, value));
            case 11 -> yearInfo.addMonthByMonth(11, new MonthInfo(monthIndex, value));
            case 12 -> yearInfo.addMonthByMonth(12, new MonthInfo(monthIndex, value));
        }
    }

    private void mapValueByMonth(YearInfo yearInfo, ParameterWithWorkingDays value) {
        double monthDailyAverage = getMonthDailyAverage(value.getValue(), value.getAmount());
        switch (value.getMonth()) {
            case 1 -> {
                yearInfo.addMonthByMonth(1, new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 2 -> {
                yearInfo.addMonthByMonth(2, new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 3 -> {
                yearInfo.addMonthByMonth(3, new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 4 -> {
                yearInfo.addMonthByMonth(4, new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 5 -> {
                yearInfo.addMonthByMonth(5, new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 6 -> {
                yearInfo.addMonthByMonth(6, new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 7 -> {
                yearInfo.addMonthByMonth(7, new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 8 -> {
                yearInfo.addMonthByMonth(8, new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 9 -> {
                yearInfo.addMonthByMonth(9, new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 10 -> {
                yearInfo.addMonthByMonth(10, new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 11 -> {
                yearInfo.addMonthByMonth(11, new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
            case 12 -> {
                yearInfo.addMonthByMonth(12, new MonthInfo(value.getMonth(), value.getValue(), value.getAmount(), monthDailyAverage));
            }
        }
    }

    private Double getMonthDailyAverage(Double value, Integer amount) {
        if (value > 0 && amount > 0) {
            return value / amount;
        }
        return 0.0;
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

    private static final String GET_COSTS_VALUE_FOR_ENTERPRISE_BY_YEAR_AND_PARAMETER_NAME = """
            SELECT ROUND((SUM(parameter_table.value) * t.price)::numeric, 2) AS value, parameter_table.year, parameter_table.month
            FROM %s parameter_table
                     INNER JOIN parameters p ON p.bean_name = '%s'
                     INNER JOIN tariffs t ON t.parameter_id = p.id
                     INNER JOIN units u ON u.id = parameter_table.unit_id
            WHERE u.parent_id = ?
              AND parameter_table.year = ?
            GROUP BY parameter_table.year, parameter_table.month, t.price
            ORDER BY parameter_table.year, parameter_table.month;""";

    @Override
    public TableData getCostsDataForEnterpriseByParameterNameAndYear(Long unitId, String parameterName, Integer year) {
        List<ParameterBaseDTO> values = jdbcTemplate.query(String.format(GET_COSTS_VALUE_FOR_ENTERPRISE_BY_YEAR_AND_PARAMETER_NAME, parameterName, parameterName),
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
