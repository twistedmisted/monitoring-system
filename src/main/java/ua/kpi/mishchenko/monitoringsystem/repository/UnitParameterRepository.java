package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.ParameterEntity;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitParameterEntity;

import java.util.List;

@Repository
public interface UnitParameterRepository extends CrudRepository<UnitParameterEntity, Long> {

    List<UnitParameterEntity> findAllByUnitId(Long unitId);

    boolean existsByUnitIdAndParameterBeanName(Long unitId, String parameterBeanName);

    void removeByUnitIdAndParameterBeanName(Long unitId, String parameterBeanName);
}
