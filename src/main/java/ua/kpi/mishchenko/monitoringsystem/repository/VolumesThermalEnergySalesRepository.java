package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesThermalEnergySalesEntity;

import java.util.List;

@Repository
public interface VolumesThermalEnergySalesRepository extends CrudRepository<VolumesThermalEnergySalesEntity, Long> {

    List<VolumesThermalEnergySalesEntity> findAllByUnitId(Long unitId, Sort sort);
}
