package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.WorkingDaysEntity;

import java.util.List;

@Repository
public interface WorkingDaysRepository extends CrudRepository<WorkingDaysEntity, Long> {

    List<WorkingDaysEntity> findAllByUnitIdAndYearOrderByYearAscMonthAsc(Long unitId, Integer year);
}
