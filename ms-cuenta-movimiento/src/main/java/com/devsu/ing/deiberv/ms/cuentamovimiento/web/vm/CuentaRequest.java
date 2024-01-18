package com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm;

import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoCuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoEstado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * CuentaRequest
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaRequest {

    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private BigDecimal saldoInicial;
    private TipoEstado estado;
    private ClienteVm cliente;

}
