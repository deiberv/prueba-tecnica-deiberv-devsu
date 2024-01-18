package com.devsu.ing.deiberv.ms.cuentamovimiento.fixture;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoCuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoEstado;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.CuentaVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * CuentasFixture
 */
public class CuentasFixture {

    public static Page<CuentaVm> obtenerListadoCuentas() {
        return new PageImpl<>(List.of(obtenerCuentaVm()), PageRequest.of(1,20), 3);
    }

    public static Page<Cuenta> obtenerListadoCuentasEntity() {
        return new PageImpl<>(List.of(obtenerCuentaEntity()), PageRequest.of(1,20), 3);
    }

    public static List<Cuenta> getListaCuentaEntity() {
        return List.of(obtenerCuentaEntity(), obtenerCuentaEntity());
    }

    public static CuentaVm obtenerCuentaVm(){
        return CuentaVm.builder()
            .cuentaId(1L)
            .cliente("Cliente Prueba")
            .numeroCuenta("9874523")
            .tipoCuenta("Corriente")
            .saldoInicial(BigDecimal.TEN)
            .estado("True")
            .build();
    }
    public static Cuenta obtenerCuentaEntity() {
        return Cuenta.builder()
            .cuentaId(123L)
            .numeroCuenta("123456")
            .tipoCuenta(TipoCuenta.CORRIENTE)
            .saldo(BigDecimal.TEN)
            .estado(TipoEstado.True)
            .build();
    }
}
