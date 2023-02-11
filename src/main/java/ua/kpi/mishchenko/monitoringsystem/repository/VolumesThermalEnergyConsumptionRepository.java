package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesThermalEnergyConsumptionEntity;

@Repository
public interface VolumesThermalEnergyConsumptionRepository extends CrudRepository<VolumesThermalEnergyConsumptionEntity, Long> {
}
