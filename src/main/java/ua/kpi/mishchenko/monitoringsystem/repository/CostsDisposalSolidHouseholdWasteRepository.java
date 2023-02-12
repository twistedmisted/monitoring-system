package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.CostsDisposalSolidHouseholdWasteEntity;

import java.util.List;

@Repository
public interface CostsDisposalSolidHouseholdWasteRepository extends CrudRepository<CostsDisposalSolidHouseholdWasteEntity, Long> {

    List<CostsDisposalSolidHouseholdWasteEntity> findAllByUnitId(Long unitId, Sort sort);
}
