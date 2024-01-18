package com.devsu.ing.deiberv.ms.cuentamovimiento.events;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Movimiento;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * MovimientoCreadoEvent
 */
@Getter
public class MovimientoCreadoEvent extends ApplicationEvent {
    private final Movimiento movimiento;
    public MovimientoCreadoEvent(Object source, Movimiento movimiento) {
        super(source);
        this.movimiento = movimiento;
    }
}
