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
@Table(name = "sections_parameters")
@Setter
@Getter
public class SectionParameterEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "amount_year", nullable = false)
    private Integer amountYear;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private SectionEntity section;

    @ManyToOne
    @JoinColumn(name = "parameter_id", nullable = false)
    private ParameterEntity parameter;
}
