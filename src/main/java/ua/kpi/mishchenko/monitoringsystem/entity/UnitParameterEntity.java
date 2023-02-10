package ua.kpi.mishchenko.monitoringsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "units_parameters")
@Setter
@Getter
public class UnitParameterEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unit;

    @ManyToOne
    @JoinColumn(name = "parameter_id", nullable = false)
    private ParameterEntity parameter;
}
