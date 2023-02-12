package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesGasolineConsumptionEntity;

import java.util.List;

@Repository
public interface VolumesGasolineConsumptionRepository extends CrudRepository<VolumesGasolineConsumptionEntity, Long> {

    List<VolumesGasolineConsumptionEntity> findAllByUnitId(Long unitId, Sort sort);
}
