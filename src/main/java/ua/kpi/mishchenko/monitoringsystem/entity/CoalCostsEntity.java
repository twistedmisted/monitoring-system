package ua.kpi.mishchenko.monitoringsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "coal_costs")
public class CoalCostsEntity extends ParametersEntity {

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitsEntity unit;
}
