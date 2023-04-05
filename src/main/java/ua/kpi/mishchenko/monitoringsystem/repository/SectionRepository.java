package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.SectionEntity;

import java.util.List;

@Repository
public interface SectionRepository extends CrudRepository<SectionEntity, Long> {

    boolean existsByName(String name);

    boolean existsByParentIdAndName(Long parentId, String name);

    List<SectionEntity> findAllByParentId(Long parentId);

    boolean existsByNameAndParentId(String name, Long parentId);
}
