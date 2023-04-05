package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.ActivityEntity;

import java.util.List;

@Repository
public interface ActivityRepository extends CrudRepository<ActivityEntity, Long> {

    List<ActivityEntity> findAllBySectionId(Long sectionId);
}
