package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesThermalEnergyConsumptionEntity;

import java.util.List;

@Repository
public interface VolumesThermalEnergyConsumptionRepository extends CrudRepository<VolumesThermalEnergyConsumptionEntity, Long> {

    List<VolumesThermalEnergyConsumptionEntity> findAllByUnitId(Long unitId, Sort sort);
}
