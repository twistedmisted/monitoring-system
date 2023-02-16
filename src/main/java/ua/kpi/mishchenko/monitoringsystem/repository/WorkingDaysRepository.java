package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.WorkingDaysEntity;

import java.util.Optional;

@Repository
public interface WorkingDaysRepository extends CrudRepository<WorkingDaysEntity, Long> {

    Optional<WorkingDaysEntity> findByUnitIdAndYear(Long unitId, Integer year);
}
