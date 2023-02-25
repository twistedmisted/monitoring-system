package ua.kpi.mishchenko.monitoringsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tariffs")
@Setter
@Getter
public class TariffEntity {

    @Id
    @Column(name = "parameter_id", nullable = false)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "parameter_id", nullable = false)
    private ParameterEntity parameter;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
