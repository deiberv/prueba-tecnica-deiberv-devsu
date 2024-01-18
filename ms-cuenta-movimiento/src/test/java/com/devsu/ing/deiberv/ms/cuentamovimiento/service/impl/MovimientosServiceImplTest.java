package com.devsu.ing.deiberv.ms.cuentamovimiento.service.impl;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Movimiento;
import com.devsu.ing.deiberv.ms.cuentamovimiento.events.publisher.MovimientoCreadoPublisher;
import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuentamovimiento.fixture.CuentasFixture;
import com.devsu.ing.deiberv.ms.cuentamovimiento.fixture.MovimientosFixture;
import com.devsu.ing.deiberv.ms.cuentamovimiento.repository.MovimientosRepository;
import com.devsu.ing.deiberv.ms.cuentamovimiento.service.CuentasService;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.MovimientoRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class MovimientosServiceImplTest {
    @InjectMocks
    private MovimientosServiceImpl movimientosService;
    @Mock
    private MovimientosRepository movimientosRepository;
    @Mock
    private CuentasService cuentasService;
    @Mock
    private MovimientoCreadoPublisher movimientoCreadoPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrar() {
        var cuenta = CuentasFixture.obtenerCuentaEntity();
        cuenta.setSaldo(BigDecimal.ZERO);

        var requets = MovimientoRequest.builder()
            .numeroCuenta("123456")
            .valor(BigDecimal.valueOf(500))
            .build();
        when(cuentasService.findByNroCuenta(anyString())).thenReturn(cuenta);
        when(movimientosRepository.save(any(Movimiento.class)))
            .thenReturn(Movimiento.builder()
                .movimientoId(12345L)
                .saldo(BigDecimal.valueOf(500))
                .build());
        doNothing().when(movimientoCreadoPublisher).publishEvent(any(Movimiento.class));
        var resultado = movimientosService.registrar(requets);
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(BigDecimal.valueOf(500), resultado.getSoldoDisponible());
    }

    @Test
    void registrarSinSaldo() {
        var requets = MovimientoRequest.builder()
            .numeroCuenta("123456")
            .valor(BigDecimal.valueOf(-1000000))
            .build();
        var cuenta = CuentasFixture.obtenerCuentaEntity();
        cuenta.setSaldo(BigDecimal.TEN);
        when(cuentasService.findByNroCuenta(anyString())).thenReturn(cuenta);
        Assertions.assertThrows(SimpleException.class, () -> movimientosService.registrar(requets));
    }

    @Test
    void reporte() {
        var fechaInicio = LocalDate.parse("2024-01-01");
        var fechaFin = LocalDate.parse("2024-01-05");
        when(movimientosRepository.findAllByClienteAndFecha(anyLong(), any(LocalDateTime.class),
            any(LocalDateTime.class), any(Sort.class))).thenReturn(MovimientosFixture.obtenerMovimientos());
        var resultado = movimientosService.reporte(123L, fechaInicio, fechaFin);
        Assertions.assertNotNull(resultado);
    }

    @ParameterizedTest
    @CsvSource({"2030-12-31,","2024-01-15,2030-12-31","2024-01-16,2024-01-01"})
    void reporteFechasInvalidas(LocalDate fechaInicio, LocalDate fechaFin) {
        Assertions.assertThrows(SimpleException.class,
            () -> movimientosService.reporte(123L, fechaInicio, fechaFin));
    }
}
