package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.COEmissionsEntity;

import java.util.List;

@Repository
public interface COEmissionsRepository extends CrudRepository<COEmissionsEntity, Long> {

    List<COEmissionsEntity> findAllByUnitId(Long unitId, Sort sort);
}
