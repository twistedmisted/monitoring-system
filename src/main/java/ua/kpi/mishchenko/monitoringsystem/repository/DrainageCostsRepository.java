package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.DrainageCostsEntity;

@Repository
public interface DrainageCostsRepository extends CrudRepository<DrainageCostsEntity, Long> {
}
