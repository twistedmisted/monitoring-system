package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.DieselFuelCostsEntity;

import java.util.List;

@Repository
public interface DieselFuelCostsRepository extends CrudRepository<DieselFuelCostsEntity, Long> {

    List<DieselFuelCostsEntity> findAllByUnitId(Long unitId, Sort sort);
}
