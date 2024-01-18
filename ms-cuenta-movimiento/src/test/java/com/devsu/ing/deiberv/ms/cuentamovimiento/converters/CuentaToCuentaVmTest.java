package com.devsu.ing.deiberv.ms.cuentamovimiento.converters;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoCuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoEstado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CuentaToCuentaVmTest {
    @InjectMocks
    private CuentaToCuentaVm cuentaToCuentaVm;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convertCuentatoCuentaVm() {
        var cuenta = Cuenta.builder()
            .cuentaId(123L)
            .cliente(Cliente.builder()
                .nombre("Cliente")
                .build())
            .numeroCuenta("123456")
            .tipoCuenta(TipoCuenta.AHORRO)
            .saldo(BigDecimal.TEN)
            .estado(TipoEstado.False)
            .numeroCuenta("123").build();
        var cuentaVm = cuentaToCuentaVm.convert(cuenta);
        Assertions.assertNotNull(cuentaVm);
        assertEquals(cuenta.getCuentaId(), cuentaVm.getCuentaId());
        assertEquals(cuenta.getNumeroCuenta(), cuentaVm.getNumeroCuenta());
        assertEquals(cuenta.getCliente().getNombre(), cuentaVm.getCliente());
    }
}
