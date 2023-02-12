package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesProductionProductsAndServicesEntity;

import java.util.List;

@Repository
public interface VolumesProductionProductsAndServicesRepository extends CrudRepository<VolumesProductionProductsAndServicesEntity, Long> {

    List<VolumesProductionProductsAndServicesEntity> findAllByUnitId(Long unitId, Sort sort);
}
