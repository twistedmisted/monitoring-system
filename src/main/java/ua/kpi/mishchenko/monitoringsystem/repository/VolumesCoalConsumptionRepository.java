package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesCoalConsumptionEntity;

import java.util.List;

@Repository
public interface VolumesCoalConsumptionRepository extends CrudRepository<VolumesCoalConsumptionEntity, Long> {

    List<VolumesCoalConsumptionEntity> findAllByUnitId(Long unitId, Sort sort);
}
