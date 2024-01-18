package com.devsu.ing.deiberv.ms.cuentamovimiento.fixture;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Movimiento;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoCuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoEstado;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.MovimientoRpt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * MovimientosFixture
 */
public class MovimientosFixture {

    public static List<Movimiento> obtenerMovimientos() {
        return List.of(
            Movimiento.builder()
                .movimientoId(1L)
                .fecha(LocalDateTime.now(ZoneId.systemDefault()))
                .cuenta(Cuenta.builder()
                    .numeroCuenta("225487")
                    .tipoCuenta(TipoCuenta.CORRIENTE)
                    .estado(TipoEstado.True)
                    .cliente(Cliente.builder()
                        .clienteId(2L)
                        .nombre("Marianela Montalvo")
                        .build())
                    .build())
                .saldoInicial(BigDecimal.valueOf(100))
                .valor(BigDecimal.valueOf(600))
                .saldo(BigDecimal.valueOf(700))
                .build()
        );
    }

    public static List<MovimientoRpt> obtenerMovimientosVm() {
        return List.of(
            MovimientoRpt.builder()
                .fecha(LocalDate.now())
                .cliente("Marianela Montalvo")
                .numeroCuenta("225487")
                .tipoCuenta("Corriente")
                .saldoInicial(BigDecimal.valueOf(100))
                .estado(true)
                .movimiento(BigDecimal.valueOf(600))
                .saldoDisponible(BigDecimal.valueOf(700))
                .build(),
            MovimientoRpt.builder()
                .fecha(LocalDate.now())
                .cliente("Marianela Montalvo")
                .numeroCuenta("496825")
                .tipoCuenta("Ahorro")
                .saldoInicial(BigDecimal.valueOf(540))
                .estado(true)
                .movimiento(BigDecimal.valueOf(-540))
                .saldoDisponible(BigDecimal.ZERO)
                .build()
        );
    }

}
