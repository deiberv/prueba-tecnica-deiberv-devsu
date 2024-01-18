package com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * MovimientoRequest
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoRequest {

    private String numeroCuenta;
    private BigDecimal valor;

}
