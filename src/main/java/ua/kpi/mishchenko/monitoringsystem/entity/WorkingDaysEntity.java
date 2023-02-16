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

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "working_days")
@Setter
@Getter
public class WorkingDaysEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unit;

    @Column(name = "year", nullable = false)
    private Integer year;

    private Integer january;
    private Integer february;
    private Integer march;
    private Integer april;
    private Integer may;
    private Integer june;
    private Integer july;
    private Integer august;
    private Integer september;
    private Integer october;
    private Integer november;
    private Integer december;
}
