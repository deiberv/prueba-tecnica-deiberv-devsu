package com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * MovimientoRpt
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoRpt {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fecha;
    private String cliente;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private boolean estado;
    private BigDecimal movimiento;
    private BigDecimal saldoDisponible;

}
