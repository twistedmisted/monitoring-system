package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.DrainageCostsEntity;

import java.util.List;

@Repository
public interface DrainageCostsRepository extends CrudRepository<DrainageCostsEntity, Long> {

    List<DrainageCostsEntity> findAllByUnitId(Long unitId, Sort sort);
}
