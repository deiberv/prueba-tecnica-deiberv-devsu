package com.devsu.ing.deiberv.ms.cuentamovimiento.events.listeners;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Movimiento;
import com.devsu.ing.deiberv.ms.cuentamovimiento.events.MovimientoCreadoEvent;
import com.devsu.ing.deiberv.ms.cuentamovimiento.fixture.CuentasFixture;
import com.devsu.ing.deiberv.ms.cuentamovimiento.repository.CuentasRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

class MovimientoCreadoListenerTest {

    @InjectMocks
    private MovimientoCreadoListener creadoListener;
    @Mock
    private CuentasRepository cuentasRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void onApplicationEvent() {
        var event = Mockito.mock(MovimientoCreadoEvent.class);
        var movimiento = Mockito.mock(Movimiento.class);
        var cuenta = CuentasFixture.obtenerCuentaEntity();
        cuenta.setSaldo(BigDecimal.valueOf(7500));
        Mockito.when(event.getMovimiento()).thenReturn(movimiento);
        Mockito.when(movimiento.getCuenta()).thenReturn(cuenta);
        Mockito.when(cuentasRepository.findByNumeroCuenta(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(cuenta));
        Mockito.when(movimiento.getValor()).thenReturn(BigDecimal.valueOf(5000));
        Mockito.when(cuentasRepository.save(ArgumentMatchers.any(Cuenta.class)))
            .thenReturn(CuentasFixture.obtenerCuentaEntity());
        creadoListener.onApplicationEvent(event);
        Mockito.verify(cuentasRepository, Mockito.times(1)).save(cuenta);
        Assertions.assertEquals(BigDecimal.valueOf(12500), cuenta.getSaldo());
    }

    @Test
    void onApplicationEventValorNegativo() {
        var event = Mockito.mock(MovimientoCreadoEvent.class);
        var movimiento = Mockito.mock(Movimiento.class);
        var cuenta = CuentasFixture.obtenerCuentaEntity();
        cuenta.setSaldo(BigDecimal.valueOf(7500));
        Mockito.when(event.getMovimiento()).thenReturn(movimiento);
        Mockito.when(movimiento.getCuenta()).thenReturn(cuenta);
        Mockito.when(cuentasRepository.findByNumeroCuenta(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(cuenta));
        Mockito.when(movimiento.getValor()).thenReturn(BigDecimal.valueOf(-5000));
        Mockito.when(cuentasRepository.save(ArgumentMatchers.any(Cuenta.class)))
            .thenReturn(CuentasFixture.obtenerCuentaEntity());
        creadoListener.onApplicationEvent(event);
        Mockito.verify(cuentasRepository, Mockito.times(1)).save(cuenta);
        Assertions.assertEquals(BigDecimal.valueOf(2500), cuenta.getSaldo());
    }

}
