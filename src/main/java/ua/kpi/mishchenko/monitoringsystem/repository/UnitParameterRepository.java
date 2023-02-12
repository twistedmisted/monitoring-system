package ua.kpi.mishchenko.monitoringsystem.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitParameterEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitParameterRepository extends CrudRepository<UnitParameterEntity, Long> {

    Optional<UnitParameterEntity> findByUnitIdAndParameterBeanName(Long unitId, String beanName);

    List<UnitParameterEntity> findAllByUnitId(Long unitId);

    boolean existsByUnitIdAndParameterBeanName(Long unitId, String parameterBeanName);

    @Transactional
    void deleteByUnitIdAndParameterBeanName(Long unitId, String parameterBeanName);
}
