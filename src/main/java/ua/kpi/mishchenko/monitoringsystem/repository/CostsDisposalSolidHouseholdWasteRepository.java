package ua.kpi.mishchenko.monitoringsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.mishchenko.monitoringsystem.entity.CostsDisposalSolidHouseholdWasteEntity;

@Repository
public interface CostsDisposalSolidHouseholdWasteRepository extends CrudRepository<CostsDisposalSolidHouseholdWasteEntity, Long> {
}
