package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesElectricityProductionEntity;

import java.util.List;

@Repository
public interface VolumesElectricityProductionRepository extends CrudRepository<VolumesElectricityProductionEntity, Long> {

    List<VolumesElectricityProductionEntity> findAllByUnitId(Long unitId, Sort sort);
}
