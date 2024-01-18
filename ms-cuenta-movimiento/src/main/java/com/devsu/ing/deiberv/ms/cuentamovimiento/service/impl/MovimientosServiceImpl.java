package com.devsu.ing.deiberv.ms.cuentamovimiento.service.impl;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Movimiento;
import com.devsu.ing.deiberv.ms.cuentamovimiento.events.publisher.MovimientoCreadoPublisher;
import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.EnumError;
import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuentamovimiento.repository.MovimientosRepository;
import com.devsu.ing.deiberv.ms.cuentamovimiento.service.CuentasService;
import com.devsu.ing.deiberv.ms.cuentamovimiento.service.MovimientosService;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.MovimientoRequest;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.MovimientoRpt;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.MovimientoSaldo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

/**
 * MovimientosServiceImpl
 */
@Service
@RequiredArgsConstructor
public class MovimientosServiceImpl implements MovimientosService {

    private final MovimientosRepository movimientosRepository;
    private final CuentasService cuentasService;
    private final MovimientoCreadoPublisher movimientoCreadoPublisher;

    @Override
    @Transactional
    public MovimientoSaldo registrar(MovimientoRequest movimientoRequest) {

        var cuenta = this.cuentasService.findByNroCuenta(movimientoRequest.getNumeroCuenta());
        var valorMovimiento = movimientoRequest.getValor();
        if (cuenta.getSaldo().add(valorMovimiento).compareTo(BigDecimal.ZERO) < 0) {
            throw new SimpleException(EnumError.SALDO_INSUFICIENTE, HttpStatus.BAD_REQUEST.value());
        }
        var movimiento = Movimiento.builder()
            .cuenta(cuenta)
            .saldoInicial(cuenta.getSaldo())
            .valor(valorMovimiento)
            .saldo(cuenta.getSaldo().add(valorMovimiento))
            .build();
        this.movimientosRepository.save(movimiento);
        this.movimientoCreadoPublisher.publishEvent(movimiento);
        return MovimientoSaldo.builder()
            .cuenta(cuenta.getNumeroCuenta())
            .idMovimiento(String.valueOf(movimiento.getMovimientoId()))
            .soldoDisponible(movimiento.getSaldo())
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoRpt> reporte(Long clienteId, LocalDate fechaInicial, LocalDate fechaFin) {

        var fechaDia = LocalDate.now(ZoneId.systemDefault());
        var fechaConsultaFin =  Objects.requireNonNullElse(fechaFin, fechaDia);
        if (fechaInicial.isAfter(fechaDia) || fechaConsultaFin.isAfter(fechaDia)) {
            throw new SimpleException(EnumError.ERROR_FECHA_INVALIDA, HttpStatus.BAD_REQUEST.value());
        }
        if (fechaConsultaFin.isBefore(fechaInicial)) {
            throw new SimpleException(EnumError.ERROR_FECHA_FIN_INVALIDA, HttpStatus.BAD_REQUEST.value());
        }

        var fechaStart = fechaInicial.atStartOfDay();
        var fechaEnd = fechaInicial.atTime(23,59,59);
        var movimientos = this.movimientosRepository.findAllByClienteAndFecha(clienteId, fechaStart, fechaEnd, Sort.by("fecha").descending());
        return movimientos.stream().map(movimiento -> MovimientoRpt.builder()
            .fecha(movimiento.getFecha().toLocalDate())
            .cliente(movimiento.getCuenta().getCliente().getNombre())
            .numeroCuenta(movimiento.getCuenta().getNumeroCuenta())
            .tipoCuenta(movimiento.getCuenta().getTipoCuenta().getDescripcion())
            .saldoInicial(movimiento.getSaldoInicial())
            .estado(movimiento.getCuenta().getEstado().toBooleanValue())
            .movimiento(movimiento.getValor())
            .saldoDisponible(movimiento.getSaldo())
            .build()).toList();
    }
}
