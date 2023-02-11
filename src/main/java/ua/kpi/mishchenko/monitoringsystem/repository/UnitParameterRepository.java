package ua.kpi.mishchenko.monitoringsystem.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitParameterEntity;

import java.util.List;

@Repository
public interface UnitParameterRepository extends CrudRepository<UnitParameterEntity, Long> {

    List<UnitParameterEntity> findAllByUnitId(Long unitId);

    boolean existsByUnitIdAndParameterBeanName(Long unitId, String parameterBeanName);

    @Transactional
    void deleteByUnitIdAndParameterBeanName(Long unitId, String parameterBeanName);
}
