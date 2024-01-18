package com.devsu.ing.deiberv.ms.cuentamovimiento.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TipoEstado
 */
@Getter
@AllArgsConstructor
public enum TipoEstado {

    True("True"),
    False("False");

    private final String descripcion;

    public boolean toBooleanValue(){
        return True.descripcion.equals(this.getDescripcion());
    }

}
