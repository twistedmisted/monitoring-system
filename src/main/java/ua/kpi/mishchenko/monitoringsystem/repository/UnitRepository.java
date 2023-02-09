package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitEntity;

@Repository
public interface UnitRepository extends CrudRepository<UnitEntity, Long> {

    boolean existsByName(String name);

    boolean existsByParentIdAndName(Long parentId, String name);
}
