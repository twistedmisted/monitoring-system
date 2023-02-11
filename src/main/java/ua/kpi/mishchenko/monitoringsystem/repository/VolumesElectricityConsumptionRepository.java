package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesElectricityConsumptionEntity;

@Repository
public interface VolumesElectricityConsumptionRepository extends CrudRepository<VolumesElectricityConsumptionEntity, Long> {
}
