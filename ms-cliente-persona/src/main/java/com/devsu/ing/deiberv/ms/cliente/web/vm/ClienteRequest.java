package com.devsu.ing.deiberv.ms.cliente.web.vm;

import com.devsu.ing.deiberv.ms.cliente.entity.EnumClienteEstado;
import com.devsu.ing.deiberv.ms.cliente.entity.EnumGenero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ClienteRequest
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {

    private String nombre;
    private EnumGenero genero;
    private Long edad;
    private String identificacion;
    private String direccion;
    private String telefono;
    private String password;
    private EnumClienteEstado estado;

}
