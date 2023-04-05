package ua.kpi.mishchenko.monitoringsystem.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.SectionParameterEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionParameterRepository extends CrudRepository<SectionParameterEntity, Long> {

    Optional<SectionParameterEntity> findBySectionIdAndParameterBeanName(Long sectionId, String beanName);

    List<SectionParameterEntity> findAllBySectionId(Long sectionId);

    boolean existsBySectionIdAndParameterBeanName(Long sectionId, String parameterBeanName);

    @Transactional
    void deleteBySectionIdAndParameterBeanName(Long sectionId, String parameterBeanName);
}
