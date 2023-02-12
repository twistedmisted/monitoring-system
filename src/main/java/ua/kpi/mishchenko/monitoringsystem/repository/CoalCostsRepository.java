package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.CoalCostsEntity;

import java.util.List;

@Repository
public interface CoalCostsRepository extends CrudRepository<CoalCostsEntity, Long> {

    List<CoalCostsEntity> findAllByUnitId(Long unitId, Sort sort);
}
