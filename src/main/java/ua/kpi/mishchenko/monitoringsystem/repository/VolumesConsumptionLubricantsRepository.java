package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesConsumptionLubricantsEntity;

import java.util.List;

@Repository
public interface VolumesConsumptionLubricantsRepository extends CrudRepository<VolumesConsumptionLubricantsEntity, Long> {

    List<VolumesConsumptionLubricantsEntity> findAllByUnitId(Long unitId, Sort sort);
}
