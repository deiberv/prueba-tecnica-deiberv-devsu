package com.devsu.ing.deiberv.ms.cliente.entity;

import lombok.Getter;

/**
 * EnumGenero
 */
@Getter

public enum EnumClienteEstado {

    TRUE,
    FALSE;

    public static String getEstadoString(EnumClienteEstado estado) {
        return TRUE.equals(estado) ? "True" : "False";
    }
}
