package ua.kpi.mishchenko.monitoringsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "units")
@Setter
@Getter
public class UnitEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Column(name = "name", nullable = false)
    private String name;

//    @OneToMany(mappedBy = "unit", fetch = LAZY, cascade = ALL)
//    private List<AmbientTemperatureEntity> ambientTemperatures = new ArrayList<>();
//
//    @OneToMany(mappedBy = "unit", fetch = LAZY, cascade = ALL)
//    private List<COEmissionsEntity> coEmissions = new ArrayList<>();
//
//    @OneToMany(mappedBy = "unit", fetch = LAZY, cascade = ALL)
//    private List<CoalCostsEntity> coalCosts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "units_parameters",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "parameter_id"))
    private List<ParameterEntity> parameters = new ArrayList<>();
}
