package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.CommentEntity;

import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Long> {

    Optional<CommentEntity> findByUnitIdAndParameterName(Long unitId, String parameterName);

    boolean existsByUnitIdAndParameterName(Long unitId, String parameterName);
}
