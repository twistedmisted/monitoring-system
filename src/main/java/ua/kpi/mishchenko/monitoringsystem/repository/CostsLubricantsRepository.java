package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.CostsLubricantsEntity;

import java.util.List;

@Repository
public interface CostsLubricantsRepository extends CrudRepository<CostsLubricantsEntity, Long> {

    List<CostsLubricantsEntity> findAllByUnitId(Long unitId, Sort sort);
}
