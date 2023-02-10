package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.dto.UnitDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.UnitEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends CrudRepository<UnitEntity, Long> {

    Optional<UnitEntity> findByIdWithParameters(Long id);

    boolean existsByName(String name);

    boolean existsByParentIdAndName(Long parentId, String name);

    List<UnitEntity> findAllByParentId(Long parentId);

    boolean existsByNameAndParentId(String name, Long parentId);
}
