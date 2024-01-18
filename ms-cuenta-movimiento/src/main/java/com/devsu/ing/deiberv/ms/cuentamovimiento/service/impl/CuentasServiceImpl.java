package com.devsu.ing.deiberv.ms.cuentamovimiento.service.impl;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoEstado;
import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.EnumError;
import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuentamovimiento.repository.CuentasRepository;
import com.devsu.ing.deiberv.ms.cuentamovimiento.service.ClientesService;
import com.devsu.ing.deiberv.ms.cuentamovimiento.service.CuentasService;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.CuentaRequest;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.CuentaVm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


/**
 * CuentasServiceImpl
 */
@Service
@RequiredArgsConstructor
public class CuentasServiceImpl implements CuentasService {

    private final CuentasRepository cuentasRepository;
    private final ConversionService conversionService;
    private final ClientesService clientesService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CuentaVm> listarCuentas(Pageable pageable) {
        return this.cuentasRepository.findAll(pageable)
            .map(cuenta -> conversionService.convert(cuenta, CuentaVm.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public CuentaVm buscarCuenta(String numeroCuenta) {
        var cuentaEntity = this.findByNroCuenta(numeroCuenta);
        return conversionService.convert(cuentaEntity, CuentaVm.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional()
    public CuentaVm crearCuenta(CuentaRequest cuentaRequest) {

        if (cuentaRequest.getSaldoInicial().compareTo(BigDecimal.ZERO) < 0) {
            throw new SimpleException(EnumError.CREAR_CUENTA_SALDO_INVALIDO, HttpStatus.BAD_REQUEST.value());
        }

        var cliente = clientesService.buscarCliente(cuentaRequest.getCliente().getClienteId());
        var cuentaEntity = Cuenta.builder()
            .cliente(cliente)
            .numeroCuenta(cuentaRequest.getNumeroCuenta())
            .tipoCuenta(cuentaRequest.getTipoCuenta())
            .saldo(cuentaRequest.getSaldoInicial())
            .estado(cuentaRequest.getEstado())
            .build();
        this.cuentasRepository.save(cuentaEntity);
        return this.conversionService.convert(cuentaEntity, CuentaVm.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional()
    public void eliminarCuenta(String numeroCuenta) {
        var cuentaEntity = this.findByNroCuenta(numeroCuenta);
        cuentaEntity.setEstado(TipoEstado.False);
        this.cuentasRepository.save(cuentaEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findByNroCuenta(String numeroCuenta) {
        return this.cuentasRepository.findByNumeroCuenta(numeroCuenta)
            .orElseThrow(() ->new SimpleException(EnumError.CUENTA_NOT_FOUND, HttpStatus.NOT_FOUND.value()));
    }

}
