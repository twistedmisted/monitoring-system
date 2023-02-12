package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.ElectricityCostsEntity;

import java.util.List;

@Repository
public interface ElectricityCostsRepository extends CrudRepository<ElectricityCostsEntity, Long> {

    List<ElectricityCostsEntity> findAllByUnitId(Long unitId, Sort sort);
}
