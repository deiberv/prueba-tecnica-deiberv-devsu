package com.devsu.ing.deiberv.ms.cuentamovimiento.service;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.CuentaRequest;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.CuentaVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * CuentasService
 */
public interface CuentasService {
    /**
     * Obtiene un listado de cuentas paginadas
     * @param pageable
     * @return {@link Page<CuentaVm>}
     */
    Page<CuentaVm> listarCuentas(Pageable pageable);

    /**
     * Obtiene detalle de una cuenta indicada
     * @param numeroCuenta {@link String}
     * @return {@link CuentaVm}
     */
    CuentaVm buscarCuenta(String numeroCuenta);

    /**
     * Crea una cuenta nueva en la base de datos
     * @param cuentaRequest  {@link CuentaRequest}
     * @return  {@link CuentaVm}
     */
    CuentaVm crearCuenta(CuentaRequest cuentaRequest);

    /**
     * Elimina una cuenta de manera logica
     * @param numeroCuenta
     */
    void eliminarCuenta(String numeroCuenta);

    Cuenta findByNroCuenta(String numeroCuenta);

}
