package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.GasExpensesEntity;

import java.util.List;

@Repository
public interface GasExpensesRepository extends CrudRepository<GasExpensesEntity, Long> {

    List<GasExpensesEntity> findAllByUnitId(Long unitId, Sort sort);
}
