package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesGasConsumptionEntity;

import java.util.List;

@Repository
public interface VolumesGasConsumptionRepository extends CrudRepository<VolumesGasConsumptionEntity, Long> {

    List<VolumesGasConsumptionEntity> findAllByUnitId(Long unitId, Sort sort);
}
