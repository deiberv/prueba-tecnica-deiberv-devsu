package com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * CuentaVm
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaVm {

    private Long cuentaId;
    private String cliente;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private String estado;

}
