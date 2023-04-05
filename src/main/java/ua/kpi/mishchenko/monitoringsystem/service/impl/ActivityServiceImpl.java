package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.ActivityBO;
import ua.kpi.mishchenko.monitoringsystem.dto.ActivityDTO;
import ua.kpi.mishchenko.monitoringsystem.dto.CreateActivity;
import ua.kpi.mishchenko.monitoringsystem.mapper.impl.ActivityMapper;
import ua.kpi.mishchenko.monitoringsystem.repository.ActivityRepository;
import ua.kpi.mishchenko.monitoringsystem.service.ActivityService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<ActivityDTO> getAllBySectionId(Long sectionId) {
        return activityMapper.entitiesToDtos(activityRepository.findAllBySectionId(sectionId));
    }

    @Override
    public List<ActivityBO> getAllByEnterpriseId(Long enterpriseId) {
        return jdbcTemplate.query("""
                SELECT u.name AS department_name, a.name, a.description, a.money, a.financial_source, p.name AS parameter_name
                FROM sections u
                         INNER JOIN activities a on u.id = a.section_id
                         INNER JOIN parameters p on a.parameter_id = p.id
                WHERE parent_id = ?;""",
                (rs, rowNum) -> new ActivityBO(
                        rs.getString("department_name"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("money"),
                        rs.getString("parameter_name"),
                        rs.getString("financial_source")
                ),
                enterpriseId);
    }

    @Override
    public void createActivity(CreateActivity createActivity) {
        activityRepository.save(activityMapper.createActivityToEntity(createActivity));
    }

    @Override
    public BigDecimal getMoneyByParameterId(Long sectionId, Long parameterId) {
        return jdbcTemplate.query("SELECT SUM(a.money) AS money FROM activities a WHERE a.section_id = ? AND a.parameter_id = ?",
                (rs, rowNum) -> rs.getBigDecimal("money"),
                sectionId, parameterId
        ).get(0);
    }

    @Override
    public BigDecimal getMoneyByParameterIdForEnterprise(Long enterpriseId, Long parameterId) {
        return jdbcTemplate.query("""
                        SELECT SUM(a.money) AS money
                        FROM sections u
                                 INNER JOIN activities a on u.id = a.section_id
                        WHERE parent_id = ? AND a.parameter_id = ?;""",
                (rs, rowNum) -> rs.getBigDecimal("money"),
                enterpriseId, parameterId
        ).get(0);
    }


}
