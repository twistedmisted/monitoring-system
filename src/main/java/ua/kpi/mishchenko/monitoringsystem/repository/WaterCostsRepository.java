package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.WaterCostsEntity;

import java.util.List;

@Repository
public interface WaterCostsRepository extends CrudRepository<WaterCostsEntity, Long> {

    List<WaterCostsEntity> findAllByUnitId(Long unitId, Sort sort);
}
