package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.DrainageVolumesEntity;

import java.util.List;

@Repository
public interface DrainageVolumesRepository extends CrudRepository<DrainageVolumesEntity, Long> {

    List<DrainageVolumesEntity> findAllByUnitId(Long unitId, Sort sort);
}
