package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesElectricityConsumptionEntity;

import java.util.List;

@Repository
public interface VolumesElectricityConsumptionRepository extends CrudRepository<VolumesElectricityConsumptionEntity, Long> {

    List<VolumesElectricityConsumptionEntity> findAllByUnitId(Long unitId, Sort sort);
}
