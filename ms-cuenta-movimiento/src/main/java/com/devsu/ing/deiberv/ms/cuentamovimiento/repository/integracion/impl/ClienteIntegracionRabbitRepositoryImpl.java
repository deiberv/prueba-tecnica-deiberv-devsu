package com.devsu.ing.deiberv.ms.cuentamovimiento.repository.integracion.impl;

import com.devsu.ing.deiberv.ms.common.events.AbstractEvent;
import com.devsu.ing.deiberv.ms.common.events.cliente.ClienteCreadoEvent;
import com.devsu.ing.deiberv.ms.common.events.cliente.ClienteEliminadoEvent;
import com.devsu.ing.deiberv.ms.common.events.cliente.ClienteModificadoEvent;
import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoEstado;
import com.devsu.ing.deiberv.ms.cuentamovimiento.repository.ClienteRepository;
import com.devsu.ing.deiberv.ms.cuentamovimiento.repository.CuentasRepository;
import com.devsu.ing.deiberv.ms.cuentamovimiento.repository.integracion.ClienteIntegracionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClienteIntegracionRabbitRepositoryImpl
 */
@Repository
@RequiredArgsConstructor
public class ClienteIntegracionRabbitRepositoryImpl implements ClienteIntegracionRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteIntegracionRabbitRepositoryImpl.class);
    private final ClienteRepository clienteRepository;
    private final CuentasRepository cuentasRepository;
    @Override
    @Transactional
    @RabbitListener(queues = "devsu.clientes.queue")
    public void procesarEvento(AbstractEvent event){
        switch (event.getAccion()) {
            case "CLIENTE_CREADO": procesarClienteCreado((ClienteCreadoEvent) event); break;
            case "CLIENTE_MODIFICADO": procesarClienteModificado((ClienteModificadoEvent) event); break;
            case "CLIENTE_ELIMINADO": procesarClienteEliminado((ClienteEliminadoEvent) event); break;
            default:
                LOGGER.warn("El evento que se ha emitido no es manejable por el servicio");
        }
    }

    private void procesarClienteCreado(ClienteCreadoEvent clienteCreadoEvent) {
        try {
            LOGGER.info("Mensaje recivido {} creando cliente", clienteCreadoEvent);
            this.clienteRepository.save(Cliente.builder()
                .clienteId(clienteCreadoEvent.getClienteId())
                .nombre(clienteCreadoEvent.getNombre())
                .build());
            LOGGER.info("Evento de cliente creado procesado de menera correcta");
        } catch (Exception exc) {
            LOGGER.info("Se ha producido un error procesando el evento de creacion {0}.", exc);
        }
    }

    private void procesarClienteModificado(ClienteModificadoEvent clienteModificadoEvent) {
        try {
            LOGGER.info("Mensaje recivido {} modificando cliente.", clienteModificadoEvent);
            this.clienteRepository.save(Cliente.builder()
                .clienteId(clienteModificadoEvent.getClienteId())
                .nombre(clienteModificadoEvent.getNombre())
                .build());
            LOGGER.info("Evento de cliente modificado procesado de menera correcta");
        } catch (Exception exc) {
            LOGGER.info("Se ha producido un error procesando el evento de modificacion {0}.", exc);
        }
    }

    private void procesarClienteEliminado(ClienteEliminadoEvent clienteEliminadoEvent) {
        try {
            LOGGER.info("Mensaje recivido {} eliminando cliente ", clienteEliminadoEvent);
            LOGGER.info("Se cambia el estado de las cuentas relacionadas al cliente");
            var cliente = this.clienteRepository.findById(clienteEliminadoEvent.getClienteId())
                .orElseThrow();
            var cuentasCliente = this.cuentasRepository.findByCliente(cliente)
                .stream().map(cuenta -> {
                    cuenta.setEstado(TipoEstado.False);
                    return cuenta;
                }).toList();
            this.cuentasRepository.saveAll(cuentasCliente);
            LOGGER.info("Evento de cliente eliminado procesado de menera correcta");
        } catch (Exception exc) {
            LOGGER.info("Se ha producido un error procesando el evento de eliminacion {0}.", exc);
        }
    }
}
