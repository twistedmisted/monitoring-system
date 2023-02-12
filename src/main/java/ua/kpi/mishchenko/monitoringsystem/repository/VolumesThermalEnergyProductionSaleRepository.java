package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesThermalEnergyProductionSaleEntity;

import java.util.List;

@Repository
public interface VolumesThermalEnergyProductionSaleRepository extends CrudRepository<VolumesThermalEnergyProductionSaleEntity, Long> {

    List<VolumesThermalEnergyProductionSaleEntity> findAllByUnitId(Long unitId, Sort sort);
}
