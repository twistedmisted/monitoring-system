package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.ParameterEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParameterRepository extends CrudRepository<ParameterEntity, Long> {

    Optional<ParameterEntity> findByBeanName(String beanName);

    Optional<ParameterEntity> findByBeanNameAndHasTariff(String beanName, Boolean hasTariff);

    @Query("""
            SELECT p
            FROM ParameterEntity p
                     INNER JOIN UnitParameterEntity up on p.id = up.parameter.id
                     INNER JOIN UnitEntity u on u.id = up.unit.id
            WHERE u.parentId = :parentId
            GROUP BY p.id, p.name, p.beanName""")
    List<ParameterEntity> findAllByUnitParentId(@Param("parentId") Long parentId);

    List<ParameterEntity> findAllByHasTariff(Boolean hasTariff);

    boolean existsByBeanNameAndHasTariff(String parameterName, Boolean hasTariff);
}
