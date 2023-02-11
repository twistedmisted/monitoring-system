package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesConsumptionLubricantsEntity;

@Repository
public interface VolumesConsumptionLubricantsRepository extends CrudRepository<VolumesConsumptionLubricantsEntity, Long> {
}
