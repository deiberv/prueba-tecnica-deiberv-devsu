package com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ClienteVm
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteVm {

    private Long clienteId;
    private String nombre;

}
