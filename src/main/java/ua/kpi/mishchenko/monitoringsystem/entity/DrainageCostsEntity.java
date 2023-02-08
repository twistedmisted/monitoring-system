package ua.kpi.mishchenko.monitoringsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "drainage_costs")
public class DrainageCostsEntity extends ParametersEntity {

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitsEntity unit;
}
