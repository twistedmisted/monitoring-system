package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesWaterConsumptionEntity;

import java.util.List;

@Repository
public interface VolumesWaterConsumptionRepository extends CrudRepository<VolumesWaterConsumptionEntity, Long> {

    List<VolumesWaterConsumptionEntity> findAllByUnitId(Long unitId, Sort sort);
}
