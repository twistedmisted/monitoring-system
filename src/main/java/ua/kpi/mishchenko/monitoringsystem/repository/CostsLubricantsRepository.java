package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.CostsLubricantsEntity;

@Repository
public interface CostsLubricantsRepository extends CrudRepository<CostsLubricantsEntity, Long> {
}
