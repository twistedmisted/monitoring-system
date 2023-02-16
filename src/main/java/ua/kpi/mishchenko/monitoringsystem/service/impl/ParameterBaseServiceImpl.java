package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.InputDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.OutputData;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterBaseDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.WorkingDaysDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.YearValue;
import ua.kpi.mishchenko.monitoringsystem.entity.ParameterBaseEntity;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitParameterEntity;
import ua.kpi.mishchenko.monitoringsystem.repository.UnitParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.service.ParameterBaseService;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ParameterBaseServiceImpl implements ParameterBaseService {

    private static final String SELECT_VALUES_FOR_PARAMETER_BY_UNIT_ID = "SELECT * FROM %s WHERE unit_id = ? ORDER BY year, month";
    private static final String SELECT_FROM_WORKING_DAYS_BY_UNIT_ID = "SELECT wd.* FROM working_days wd WHERE unit_id = 13 ORDER BY year";
    private final JdbcTemplate jdbcTemplate;
    private final UnitParameterRepository unitParameterRepository;

    @Override
    public InputDTO getDataByParameterName(Long unitId, String parameterName) {
        List<ParameterBaseEntity> values = getValuesForParameterByUnitId(unitId, parameterName);
        if (values.isEmpty()) {
            return createDefaultTableDataForInput();
        }
        List<YearValue> yearValues = new ArrayList<>();
        YearValue yearValue = new YearValue();
        for (ParameterBaseEntity parameterBase : values) {
            if (isNotDefaultYear(yearValue) &&
                    !yearValue.getYear().equals(String.valueOf(parameterBase.getYear()))) {
                yearValues.add(yearValue);
                yearValue = new YearValue();
            }
            yearValue.setYear(String.valueOf(parameterBase.getYear()));
            mapValueByMonth(yearValue, parameterBase.getMonth(), parameterBase.getValue());
        }
        yearValues.add(yearValue);
        UnitParameterEntity unitParameter = unitParameterRepository.findByUnitIdAndParameterBeanName(unitId, parameterName)
                .orElse(null);
        if (unitParameter != null && unitParameter.getAmountYear() != yearValues.size()) {
            yearValues.addAll(Collections.nCopies(unitParameter.getAmountYear() - yearValues.size(), new YearValue()));
        }
        InputDTO tableData = new InputDTO();
        tableData.setYearValues(yearValues);
        return tableData;
    }

    private InputDTO createDefaultTableDataForInput() {
        InputDTO tableData = new InputDTO();
        tableData.setYearValues(new ArrayList<>(Collections.nCopies(10, new YearValue())));
        return tableData;
    }

    @Override
    public OutputData getDataByParameterNameWithWorkingDays(Long unitId, String parameterName) {
        List<ParameterBaseEntity> values = getValuesForParameterByUnitId(unitId, parameterName);
        List<WorkingDaysDTO> workingDays = getWorkingDaysByUnitId(unitId);
        if (values.isEmpty()) {
            return createEmptyTableDataForOutput();
        }
        List<YearValue> yearValues = new ArrayList<>();
        YearValue yearValue = new YearValue();
        for (ParameterBaseEntity parameterBase : values) {
            if (isNotDefaultYear(yearValue) &&
                    !yearValue.getYear().equals(String.valueOf(parameterBase.getYear()))) {
                yearValues.add(yearValue);
                yearValue = new YearValue();
            }
            yearValue.setYear(String.valueOf(parameterBase.getYear()));
            mapValueByMonth(yearValue, parameterBase.getMonth(), parameterBase.getValue());
        }
        yearValues.add(yearValue);
        UnitParameterEntity unitParameter = unitParameterRepository.findByUnitIdAndParameterBeanName(unitId, parameterName)
                .orElse(null);
        if (unitParameter != null && unitParameter.getAmountYear() != yearValues.size()) {
            yearValues.addAll(Collections.nCopies(unitParameter.getAmountYear() - yearValues.size(), new YearValue()));
        }
        OutputData tableData = new OutputData();
        tableData.setYearValues(yearValues);
        return tableData;
    }

    private OutputData createEmptyTableDataForOutput() {
        OutputData tableData = new OutputData();
        tableData.setYearValues(new ArrayList<>(Collections.nCopies(10, new YearValue())));
        tableData.setWorkingDays(new ArrayList<>(Collections.nCopies(10, new WorkingDaysDTO())));
        return tableData;
    }

    private List<WorkingDaysDTO> getWorkingDaysByUnitId(Long unitId) {
        return jdbcTemplate.query(SELECT_FROM_WORKING_DAYS_BY_UNIT_ID,
                (rs, rowNum) -> new WorkingDaysDTO(
                        rs.getLong("id"),
                        rs.getLong("unitId"),
                        rs.getInt("year"),
                        rs.getInt("january"),
                        rs.getInt("february"),
                        rs.getInt("march"),
                        rs.getInt("april"),
                        rs.getInt("may"),
                        rs.getInt("june"),
                        rs.getInt("july"),
                        rs.getInt("august"),
                        rs.getInt("september"),
                        rs.getInt("october"),
                        rs.getInt("november"),
                        rs.getInt("december")),
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
    public InputDTO getDataForEnterpriseByParameterName(Long unitId, String parameterName) {
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
        InputDTO tableData = new InputDTO();
        if (values.isEmpty()) {
            tableData.setYearValues(new ArrayList<>(Collections.nCopies(10, new YearValue())));
            return tableData;
        }
        return getTableData(unitId, parameterName, values);
    }

    private InputDTO getTableData(Long unitId, String parameterName, List<ParameterBaseDTO> values) {
        List<YearValue> yearValues = mapParameterDtosToYearValues(values);
        InputDTO tableData = new InputDTO();
        tableData.setYearValues(yearValues);
        return tableData;
    }

    private List<YearValue> mapParameterDtosToYearValues(List<ParameterBaseDTO> values) {
        List<YearValue> yearValues = new ArrayList<>();
        YearValue yearValue = new YearValue();
        for (ParameterBaseDTO parameterBase : values) {
            if (isNotDefaultYear(yearValue) && yearsNotEquals(yearValue, parameterBase)) {
                yearValues.add(yearValue);
                yearValue = new YearValue();
            }
            yearValue.setYear(String.valueOf(parameterBase.getYear()));
            mapValueByMonth(yearValue, parameterBase.getMonth(), parameterBase.getValue());
        }
        yearValues.add(yearValue);
        return yearValues;
    }

    private static boolean yearsNotEquals(YearValue yearValue, ParameterBaseDTO parameterBase) {
        return !yearValue.getYear().equals(String.valueOf(parameterBase.getYear()));
    }

    private boolean isNotDefaultYear(YearValue yearValue) {
        return !yearValue.getYear().equals("xxxx");
    }

    private void mapValueByMonth(YearValue yearValue, int monthIndex, Double value) {
        switch (monthIndex) {
            case 1 -> yearValue.setJanuary(YearValue.toStringValue(value));
            case 2 -> yearValue.setFebruary(YearValue.toStringValue(value));
            case 3 -> yearValue.setMarch(YearValue.toStringValue(value));
            case 4 -> yearValue.setApril(YearValue.toStringValue(value));
            case 5 -> yearValue.setMay(YearValue.toStringValue(value));
            case 6 -> yearValue.setJune(YearValue.toStringValue(value));
            case 7 -> yearValue.setJuly(YearValue.toStringValue(value));
            case 8 -> yearValue.setAugust(YearValue.toStringValue(value));
            case 9 -> yearValue.setSeptember(YearValue.toStringValue(value));
            case 10 -> yearValue.setOctober(YearValue.toStringValue(value));
            case 11 -> yearValue.setNovember(YearValue.toStringValue(value));
            case 12 -> yearValue.setDecember(YearValue.toStringValue(value));
        }
    }

    @Override
    public InputDTO getDataForEnterpriseByParameterNameAndYear(Long unitId, String parameterName, Integer year) {
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
            InputDTO tableData = new InputDTO();
            tableData.setYearValues(new ArrayList<>(Collections.nCopies(1, new YearValue())));
            return tableData;
        }
        return getTableData(unitId, parameterName, values);
    }

    @Override
    public void saveData(Long unitId, String parameterName, InputDTO tableData) {
        jdbcTemplate.update("DELETE FROM " + parameterName + " WHERE unit_id = ?", unitId);
        for (YearValue yearValue : tableData.getYearValues()) {
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 1, YearValue.toDoubleValue(yearValue.getJanuary()));
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 2, YearValue.toDoubleValue(yearValue.getFebruary()));
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 3, YearValue.toDoubleValue(yearValue.getMarch()));
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 4, YearValue.toDoubleValue(yearValue.getApril()));
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 5, YearValue.toDoubleValue(yearValue.getMay()));
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 6, YearValue.toDoubleValue(yearValue.getJune()));
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 7, YearValue.toDoubleValue(yearValue.getJuly()));
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 8, YearValue.toDoubleValue(yearValue.getAugust()));
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 9, YearValue.toDoubleValue(yearValue.getSeptember()));
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 10, YearValue.toDoubleValue(yearValue.getOctober()));
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 11, YearValue.toDoubleValue(yearValue.getNovember()));
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 12, YearValue.toDoubleValue(yearValue.getDecember()));
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
