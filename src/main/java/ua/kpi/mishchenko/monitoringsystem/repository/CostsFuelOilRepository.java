package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.CostsFuelOilEntity;

import java.util.List;

@Repository
public interface CostsFuelOilRepository extends CrudRepository<CostsFuelOilEntity, Long> {

    List<CostsFuelOilEntity> findAllByUnitId(Long unitId, Sort sort);
}
