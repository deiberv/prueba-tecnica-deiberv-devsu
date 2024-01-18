package com.devsu.ing.deiberv.ms.common.events.cliente;


import com.devsu.ing.deiberv.ms.common.events.AbstractEvent;
import lombok.Getter;

/**
 * ClienteCreadoEvent
 */
@Getter
public class ClienteCreadoEvent extends AbstractEvent {

    private Long clienteId;
    private String nombre;

    public ClienteCreadoEvent(Long clienteId, String nombre) {
        super("CLIENTE_CREADO");
        this.clienteId = clienteId;
        this.nombre = nombre;
    }

}
