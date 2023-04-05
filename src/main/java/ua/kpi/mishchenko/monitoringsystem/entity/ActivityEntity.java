package ua.kpi.mishchenko.monitoringsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "activities")
@Getter
@Setter
public class ActivityEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "money", nullable = false)
    private BigDecimal money;

    @Column(name = "financial_source", nullable = false)
    private String financialSource;

    @ManyToOne
    @JoinColumn(name = "parameter_id", nullable = false)
    private ParameterEntity parameter;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private SectionEntity section;
}
