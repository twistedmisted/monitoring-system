package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.HeatEnergyCostsOwnNeedsEntity;

import java.util.List;

@Repository
public interface HeatEnergyCostsOwnNeedsRepository extends CrudRepository<HeatEnergyCostsOwnNeedsEntity, Long> {

    List<HeatEnergyCostsOwnNeedsEntity> findAllByUnitId(Long unitId, Sort sort);
}
