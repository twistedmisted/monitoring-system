package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.VolumesSolidHouseholdWasteEntity;

@Repository
public interface VolumesSolidHouseholdWasteRepository extends CrudRepository<VolumesSolidHouseholdWasteEntity, Long> {
}
