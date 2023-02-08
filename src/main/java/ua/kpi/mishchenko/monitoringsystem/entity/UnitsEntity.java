package ua.kpi.mishchenko.monitoringsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "units")
@Setter
@Getter
public class UnitsEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "unit", fetch = LAZY, cascade = ALL)
    private List<AmbientTemperatureEntity> ambientTemperatures = new ArrayList<>();

    @OneToMany(mappedBy = "unit", fetch = LAZY, cascade = ALL)
    private List<COEmissionsEntity> coEmissions = new ArrayList<>();

    @OneToMany(mappedBy = "unit", fetch = LAZY, cascade = ALL)
    private List<CoalCostsEntity> coalCosts = new ArrayList<>();

}
