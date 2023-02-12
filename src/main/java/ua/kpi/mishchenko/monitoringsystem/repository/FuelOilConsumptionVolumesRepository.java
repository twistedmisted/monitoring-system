package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.FuelOilConsumptionVolumesEntity;

import java.util.List;

@Repository
public interface FuelOilConsumptionVolumesRepository extends CrudRepository<FuelOilConsumptionVolumesEntity, Long> {

    List<FuelOilConsumptionVolumesEntity> findAllByUnitId(Long unitId, Sort sort);
}
