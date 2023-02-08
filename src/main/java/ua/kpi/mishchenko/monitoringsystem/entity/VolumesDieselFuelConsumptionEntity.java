package ua.kpi.mishchenko.monitoringsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "volumes_diesel_fuel_consumption")
public class VolumesDieselFuelConsumptionEntity extends ParametersEntity {

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitsEntity unit;
}
