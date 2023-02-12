package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.InputDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.YearValue;
import ua.kpi.mishchenko.monitoringsystem.entity.ParameterBaseEntity;
import ua.kpi.mishchenko.monitoringsystem.service.ParameterBaseService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParameterBaseServiceImpl implements ParameterBaseService {

    private final BeanFactory beanFactory;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public InputDTO getDataByParameterName(Long unitId, String parameterName) {
        String repositoryName = createRepositoryNameFromParameterName(parameterName);
        Object repository = beanFactory.getBean(repositoryName);
        Sort sort = Sort.by("year").ascending().and(Sort.by("month").ascending());
        try {
            List<ParameterBaseEntity> values = (List<ParameterBaseEntity>) repository.getClass().getMethod("findAllByUnitId", Long.class, Sort.class).invoke(repository, unitId, sort);
            InputDTO tableData = new InputDTO();
            if (values.isEmpty()) {
                tableData.setYearValues(new ArrayList<>(Collections.nCopies(10, new YearValue())));
                return tableData;
            }
            List<YearValue> yearValues = new ArrayList<>();
            YearValue yearValue = new YearValue();
            for (ParameterBaseEntity parameterBase : values) {
                if (!yearValue.getYear().equals("xxxx") &&
                        !yearValue.getYear().equals(String.valueOf(parameterBase.getYear()))) {
                    yearValues.add(yearValue);
                    yearValue = new YearValue();
                }
                yearValue.setYear(String.valueOf(parameterBase.getYear()));
                switch (parameterBase.getMonth()) {
                    case 1 -> yearValue.setJanuary(parameterBase.getValue());
                    case 2 -> yearValue.setFebruary(parameterBase.getValue());
                    case 3 -> yearValue.setMarch(parameterBase.getValue());
                    case 4 -> yearValue.setApril(parameterBase.getValue());
                    case 5 -> yearValue.setMay(parameterBase.getValue());
                    case 6 -> yearValue.setJune(parameterBase.getValue());
                    case 7 -> yearValue.setJuly(parameterBase.getValue());
                    case 8 -> yearValue.setAugust(parameterBase.getValue());
                    case 9 -> yearValue.setSeptember(parameterBase.getValue());
                    case 10 -> yearValue.setOctober(parameterBase.getValue());
                    case 11 -> yearValue.setNovember(parameterBase.getValue());
                    case 12 -> yearValue.setDecember(parameterBase.getValue());
                }
            }
            yearValues.add(yearValue);
            tableData.setYearValues(yearValues);
            return tableData;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveData(Long unitId, String parameterName, InputDTO tableData) {
        jdbcTemplate.update("DELETE FROM " + parameterName + " WHERE unit_id = ?", unitId);
        for (YearValue yearValue : tableData.getYearValues()) {
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 1, yearValue.getJanuary());
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 2, yearValue.getFebruary());
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 3, yearValue.getMarch());
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 4, yearValue.getApril());
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 5, yearValue.getMay());
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 6, yearValue.getJune());
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 7, yearValue.getJuly());
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 8, yearValue.getAugust());
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 9, yearValue.getSeptember());
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 10, yearValue.getOctober());
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 11, yearValue.getNovember());
            save(unitId, parameterName, Integer.parseInt(yearValue.getYear()), 12, yearValue.getDecember());
        }
//        String repositoryName = createRepositoryNameFromParameterName(parameterName);
//        String entityName = createEntityNameFromParameterName(parameterName);
//        Object entityObject = beanFactory.getBean(entityName);
//        Class<?> entityClass = entityObject.getClass();
//        for (YearValue yearValue : tableData.getYearValues()) {
//            entityClass.getMethod("setYear", Integer.class).invoke(entityObject, yearValue.getYear());
//        }
////        repository.getClass().getMethod("save", Long.class, Sort.class).invoke(repository, unitId, sort);
    }

    private void save(Long unitId, String tableName, Integer year, Integer month, Double value) {
        jdbcTemplate.update("INSERT INTO " + tableName + " (year, month, value, unit_id) VALUES (?, ?, ?, ?)",
                year,
                month,
                value,
                unitId);
    }

    private String createEntityNameFromParameterName(String parameterName) {
        StringBuilder sb = getClassName(parameterName);
        sb.append("Entity");
        return sb.toString();
    }

    private String createRepositoryNameFromParameterName(String parameterName) {
        StringBuilder sb = getClassName(parameterName);
        sb.append("Repository");
        return sb.toString();
    }

    private StringBuilder getClassName(String parameterName) {
        StringBuilder sb = new StringBuilder(parameterName);
//        sb.replace(0, 1, String.valueOf(Character.toUpperCase(sb.charAt(0))));
        while (true) {
            int index = sb.indexOf("_");
            if (index == -1) {
                break;
            }
            int indexOfLetter = index + 1;
            sb.replace(indexOfLetter, indexOfLetter + 1, String.valueOf(Character.toUpperCase(sb.charAt(indexOfLetter))));
            sb.deleteCharAt(index);
        }
        return sb;
    }
}
