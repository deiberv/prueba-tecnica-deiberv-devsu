package com.devsu.ing.deiberv.ms.common.events.cliente;

import com.devsu.ing.deiberv.ms.common.events.AbstractEvent;
import lombok.Getter;

/**
 * ClienteModificadoEvent
 */
@Getter
public class ClienteModificadoEvent extends AbstractEvent {

    private Long clienteId;
    private String nombre;

    public ClienteModificadoEvent(Long clienteId, String nombre) {
        super("CLIENTE_MODIFICADO");
        this.clienteId = clienteId;
        this.nombre = nombre;
    }

}
