package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesSolidHouseholdWasteEntity;

import java.util.List;

@Repository
public interface VolumesSolidHouseholdWasteRepository extends CrudRepository<VolumesSolidHouseholdWasteEntity, Long> {

    List<VolumesSolidHouseholdWasteEntity> findAllByUnitId(Long unitId, Sort sort);
}
