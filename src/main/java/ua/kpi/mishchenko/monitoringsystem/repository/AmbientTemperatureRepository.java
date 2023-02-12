package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.AmbientTemperatureEntity;

import java.util.List;

@Repository
public interface AmbientTemperatureRepository extends CrudRepository<AmbientTemperatureEntity, Long> {

    List<AmbientTemperatureEntity> findAllByUnitId(Long unitId, Sort sort);
}
