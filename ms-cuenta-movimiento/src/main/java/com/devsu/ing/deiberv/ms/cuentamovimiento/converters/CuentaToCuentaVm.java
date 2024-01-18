package com.devsu.ing.deiberv.ms.cuentamovimiento.converters;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.CuentaVm;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * CuentaToCuentaVm
 */
@Component
public class CuentaToCuentaVm implements Converter<Cuenta, CuentaVm> {
    @Override
    public CuentaVm convert(Cuenta source) {
        return CuentaVm.builder()
            .cuentaId(source.getCuentaId())
            .cliente(source.getCliente().getNombre())
            .numeroCuenta(source.getNumeroCuenta())
            .tipoCuenta(source.getTipoCuenta().getDescripcion())
            .saldoInicial(source.getSaldo())
            .estado(source.getEstado().getDescripcion())
            .build();
    }
}
