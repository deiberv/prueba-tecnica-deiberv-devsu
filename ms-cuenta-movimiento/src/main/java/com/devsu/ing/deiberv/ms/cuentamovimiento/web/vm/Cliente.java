package com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Cliente
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    private Long clienteId;
    private String nombre;

}
