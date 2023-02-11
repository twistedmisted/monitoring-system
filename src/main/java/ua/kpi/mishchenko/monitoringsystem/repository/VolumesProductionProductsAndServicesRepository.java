package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesProductionProductsAndServicesEntity;

@Repository
public interface VolumesProductionProductsAndServicesRepository extends CrudRepository<VolumesProductionProductsAndServicesEntity, Long> {
}
