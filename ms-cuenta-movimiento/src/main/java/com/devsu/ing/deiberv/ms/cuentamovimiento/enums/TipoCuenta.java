package com.devsu.ing.deiberv.ms.cuentamovimiento.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TipoCuenta
 */
@Getter
@AllArgsConstructor
public enum TipoCuenta {

    CORRIENTE("Corriente"),
    AHORRO("Ahorro");

    private final String descripcion;

}
