package ua.kpi.mishchenko.monitoringsystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TariffDTO {

    private Long id;
    private BigDecimal price;
}
