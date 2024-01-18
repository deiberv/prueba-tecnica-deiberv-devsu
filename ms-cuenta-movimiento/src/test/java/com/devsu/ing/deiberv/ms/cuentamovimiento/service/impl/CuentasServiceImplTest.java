package com.devsu.ing.deiberv.ms.cuentamovimiento.service.impl;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoCuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoEstado;
import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuentamovimiento.fixture.ClientesFixture;
import com.devsu.ing.deiberv.ms.cuentamovimiento.fixture.CuentasFixture;
import com.devsu.ing.deiberv.ms.cuentamovimiento.repository.CuentasRepository;
import com.devsu.ing.deiberv.ms.cuentamovimiento.service.ClientesService;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.ClienteVm;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.CuentaRequest;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.CuentaVm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CuentasServiceImplTest {

    @Mock
    private CuentasRepository cuentasRepository;
    @Mock
    private ClientesService clientesService;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private CuentasServiceImpl cuentasService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarCuentas() {
        var cuentaEntity = Cuenta.builder().build();
        Mockito.when(cuentasRepository.findAll(any(Pageable.class)))
            .thenReturn(CuentasFixture.obtenerListadoCuentasEntity());
        Mockito.when(conversionService.convert(cuentaEntity, CuentaVm.class))
            .thenReturn(CuentasFixture.obtenerCuentaVm());
        var resultado = cuentasService.listarCuentas(PageRequest.of(1,20));
        Assertions.assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void buscarCuenta() {
        var cuenta = Optional.of(CuentasFixture.obtenerCuentaEntity());
        var cuentaEntity = Cuenta.builder().build();
        Mockito.when(cuentasRepository.findByNumeroCuenta(ArgumentMatchers.anyString()))
            .thenReturn(cuenta);
        Mockito.when(conversionService.convert(cuentaEntity, CuentaVm.class))
            .thenReturn(CuentasFixture.obtenerCuentaVm());
        var resultado = cuentasService.findByNroCuenta("123456");
        Assertions.assertNotNull(resultado);
    }
    @Test
    void crearCuenta() {
        var crearCtaRequest = CuentaRequest.builder()
            .numeroCuenta("12345678")
            .tipoCuenta(TipoCuenta.CORRIENTE)
            .saldoInicial(BigDecimal.valueOf(75000))
            .estado(TipoEstado.True)
            .cliente(ClienteVm.builder()
                .clienteId(15963L)
                .nombre("Nombre cliente")
                .build())
            .build();
        var cuentaEntity = Cuenta.builder()
            .cliente(ClientesFixture.getClienteEntity())
            .numeroCuenta("12345678")
            .tipoCuenta(TipoCuenta.CORRIENTE)
            .saldo(BigDecimal.valueOf(75000))
            .estado(TipoEstado.True)
            .build();
        Mockito.when(clientesService.buscarCliente(any(Long.class)))
            .thenReturn(ClientesFixture.getClienteEntity());
        Mockito.when(conversionService.convert(cuentaEntity, CuentaVm.class))
            .thenReturn(CuentasFixture.obtenerCuentaVm());
        var resultado = cuentasService.crearCuenta(crearCtaRequest);
        verify(this.cuentasRepository, times(1)).save(any(Cuenta.class));
        Assertions.assertNotNull(resultado);
    }

    @Test
    void crearCuentaSaldoNegativo() {
        var crearCtaRequest = CuentaRequest.builder()
            .saldoInicial(BigDecimal.valueOf(-1))
            .build();
        Assertions.assertThrows(SimpleException.class, () -> cuentasService.crearCuenta(crearCtaRequest));
    }

    @Test
    void eliminarCuenta() {
        var cuenta = CuentasFixture.obtenerCuentaEntity();
        Mockito.when(cuentasRepository.findByNumeroCuenta(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(cuenta));
        cuentasService.eliminarCuenta("123456");
        verify(this.cuentasRepository, times(1)).save(cuenta);
        Assertions.assertEquals(TipoEstado.False, cuenta.getEstado());
    }

    @Test
    void findByNroCuenta() {
        var cuenta = Optional.of(CuentasFixture.obtenerCuentaEntity());
        Mockito.when(cuentasRepository.findByNumeroCuenta(ArgumentMatchers.anyString()))
            .thenReturn(cuenta);
        var cuentaEntity = cuentasService.findByNroCuenta("12345678");
        Assertions.assertNotNull(cuentaEntity);
        Assertions.assertEquals(cuenta.get(), cuentaEntity);
    }

    @Test
    void findByNroCuentaException() {
        Mockito.when(cuentasRepository.findByNumeroCuenta(ArgumentMatchers.anyString()))
            .thenReturn(Optional.empty());
        Assertions.assertThrows(SimpleException.class, () -> cuentasService.findByNroCuenta("12345678"));
    }

}
