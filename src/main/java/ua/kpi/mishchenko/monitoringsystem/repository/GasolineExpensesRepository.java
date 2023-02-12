package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.GasolineExpensesEntity;

import java.util.List;

@Repository
public interface GasolineExpensesRepository extends CrudRepository<GasolineExpensesEntity, Long> {

    List<GasolineExpensesEntity> findAllByUnitId(Long unitId, Sort sort);
}
