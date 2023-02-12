package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.CostsElectricityProductionEntity;

import java.util.List;

@Repository
public interface CostsElectricityProductionRepository extends CrudRepository<CostsElectricityProductionEntity, Long> {

    List<CostsElectricityProductionEntity> findAllByUnitId(Long unitId, Sort sort);
}
