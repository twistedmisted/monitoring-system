package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesDieselFuelConsumptionEntity;

import java.util.List;

@Repository
public interface VolumesDieselFuelConsumptionRepository extends CrudRepository<VolumesDieselFuelConsumptionEntity, Long> {

    List<VolumesDieselFuelConsumptionEntity> findAllByUnitId(Long unitId, Sort sort);
}
