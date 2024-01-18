package com.devsu.ing.deiberv.ms.cuentamovimiento.service;

import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.MovimientoRequest;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.MovimientoRpt;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.MovimientoSaldo;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

/**
 * MovimientosService
 */
public interface MovimientosService {

    /**
     * Realiza el registro de un movimiento sobre la cuenta
     * @param movimientoRequest {@link MovimientoRequest}
     * @return {@link MovimientoSaldo}
     */
    MovimientoSaldo registrar(MovimientoRequest movimientoRequest);

    /**
     * Genera el reporte de movimientos para un cliente
     * @param clienteId {@link Long}
     * @param fechaInicial {@link LocalDate}
     * @param fechaFin {@link LocalDate}
     * @return Lista de movimientos {@link List<MovimientoRpt>}
     */
    List<MovimientoRpt> reporte(Long clienteId, LocalDate fechaInicial, @Nullable LocalDate fechaFin);
}
