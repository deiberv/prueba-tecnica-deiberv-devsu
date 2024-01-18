package com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * MovimientoSaldo
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoSaldo {

    private String idMovimiento;
    private String cuenta;
    private BigDecimal soldoDisponible;

}
