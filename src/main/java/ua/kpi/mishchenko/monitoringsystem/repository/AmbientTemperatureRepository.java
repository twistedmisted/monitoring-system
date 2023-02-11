package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.AmbientTemperatureEntity;

@Repository
public interface AmbientTemperatureRepository extends CrudRepository<AmbientTemperatureEntity, Long> {
}
