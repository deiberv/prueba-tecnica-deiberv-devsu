package com.devsu.ing.deiberv.ms.common.events.cliente;

import com.devsu.ing.deiberv.ms.common.events.AbstractEvent;
import lombok.Getter;

/**
 * ClienteEliminadoEvent
 */
@Getter
public class ClienteEliminadoEvent extends AbstractEvent {

    private Long clienteId;
    private String nombre;
    public ClienteEliminadoEvent(Long clienteId, String nombre) {
        super("CLIENTE_ELIMINADO");
        this.clienteId = clienteId;
        this.nombre = nombre;
    }

}
