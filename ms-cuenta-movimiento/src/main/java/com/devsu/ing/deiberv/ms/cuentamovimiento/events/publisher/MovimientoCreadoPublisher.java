package com.devsu.ing.deiberv.ms.cuentamovimiento.events.publisher;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Movimiento;
import com.devsu.ing.deiberv.ms.cuentamovimiento.events.MovimientoCreadoEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * MovimientoCreadoPublisher
 */
@Component
@RequiredArgsConstructor
public class MovimientoCreadoPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovimientoCreadoPublisher.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(final Movimiento movimiento) {
        LOGGER.info("Publicando evento de movimiento creado.");
        MovimientoCreadoEvent movimientoCreadoEvent = new MovimientoCreadoEvent(this, movimiento);
        applicationEventPublisher.publishEvent(movimientoCreadoEvent);
    }

}
