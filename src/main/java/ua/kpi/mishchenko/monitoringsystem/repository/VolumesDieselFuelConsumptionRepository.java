package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesDieselFuelConsumptionEntity;

@Repository
public interface VolumesDieselFuelConsumptionRepository extends CrudRepository<VolumesDieselFuelConsumptionEntity, Long> {
}
