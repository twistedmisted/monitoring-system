package ua.kpi.mishchenko.monitoringsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "parameters")
@Setter
@Getter
public class ParameterEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "bean_name", nullable = false)
    private String beanName;

    @Column(name = "has_tariff", nullable = false)
    private Boolean hasTariff;

    @OneToMany(mappedBy = "parameter", cascade = ALL)
    private List<UnitParameterEntity> units = new ArrayList<>();

    @OneToOne(mappedBy = "parameter", cascade = ALL)
    @PrimaryKeyJoinColumn
    private TariffEntity tariff;
}
