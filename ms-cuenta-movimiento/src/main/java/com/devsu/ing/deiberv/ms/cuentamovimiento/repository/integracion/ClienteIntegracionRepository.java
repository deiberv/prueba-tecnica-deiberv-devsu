package com.devsu.ing.deiberv.ms.cuentamovimiento.repository.integracion;

import com.devsu.ing.deiberv.ms.common.events.AbstractEvent;

/**
 * ClienteIntegracionRepository
 */
public interface ClienteIntegracionRepository {

    void procesarEvento(AbstractEvent evento);

}
