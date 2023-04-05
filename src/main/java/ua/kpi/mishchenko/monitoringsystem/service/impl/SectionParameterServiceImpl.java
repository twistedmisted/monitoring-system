package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.ParameterDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.SectionParameterEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.impl.ParameterMapper;
import ua.kpi.mishchenko.monitoringsystem.repository.ParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.repository.SectionParameterRepository;
import ua.kpi.mishchenko.monitoringsystem.service.SectionParameterService;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class SectionParameterServiceImpl implements SectionParameterService {

    private final SectionParameterRepository sectionParameterRepository;
    private final ParameterRepository parameterRepository;
    private final ParameterMapper parameterMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getAllBeanNameParametersBySectionId(Long sectionId) {
        return parameterMapper.upEntitiesToBeanNameString(sectionParameterRepository.findAllBySectionId(sectionId));
    }

    @Override
    public List<ParameterDTO> getAllParametersBySectionId(Long sectionId) {
        return parameterMapper.upEntitiesToParameterDto(sectionParameterRepository.findAllBySectionId(sectionId));
    }

    @Override
    public void addYear(Long sectionId, String parameterName) {
        SectionParameterEntity sectionParameter = sectionParameterRepository.findBySectionIdAndParameterBeanName(sectionId, parameterName)
                .orElse(null);
        if (isNull(sectionParameter)) {
            return;
        }
        sectionParameter.setAmountYear(sectionParameter.getAmountYear() + 1);
        sectionParameterRepository.save(sectionParameter);
    }

    @Override
    public void removeYear(Long sectionId, String parameterName, Integer year) {
        SectionParameterEntity sectionParameter = sectionParameterRepository.findBySectionIdAndParameterBeanName(sectionId, parameterName)
                .orElse(null);
        if (isNull(sectionParameter)) {
            return;
        }
        sectionParameter.setAmountYear(sectionParameter.getAmountYear() - 1);
        sectionParameterRepository.save(sectionParameter);
        jdbcTemplate.update("DELETE FROM " + parameterName + " WHERE section_id = ? AND year = ?", sectionId, year);
    }

    @Override
    public List<ParameterDTO> getAllParametersByEnterpriseId(Long enterpriseId) {
        return parameterMapper.entitiesToDtos(parameterRepository.findAllBySectionParentId(enterpriseId));
    }
}
