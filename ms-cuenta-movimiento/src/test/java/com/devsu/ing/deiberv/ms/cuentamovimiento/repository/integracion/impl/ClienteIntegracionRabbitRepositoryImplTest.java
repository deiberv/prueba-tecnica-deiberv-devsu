package com.devsu.ing.deiberv.ms.cuentamovimiento.repository.integracion.impl;

import com.devsu.ing.deiberv.ms.common.events.AbstractEvent;
import com.devsu.ing.deiberv.ms.common.events.cliente.ClienteCreadoEvent;
import com.devsu.ing.deiberv.ms.common.events.cliente.ClienteEliminadoEvent;
import com.devsu.ing.deiberv.ms.common.events.cliente.ClienteModificadoEvent;
import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoEstado;
import com.devsu.ing.deiberv.ms.cuentamovimiento.fixture.ClientesFixture;
import com.devsu.ing.deiberv.ms.cuentamovimiento.fixture.CuentasFixture;
import com.devsu.ing.deiberv.ms.cuentamovimiento.repository.ClienteRepository;
import com.devsu.ing.deiberv.ms.cuentamovimiento.repository.CuentasRepository;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.Optional;

@ExtendWith(OutputCaptureExtension.class)
class ClienteIntegracionRabbitRepositoryImplTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private CuentasRepository cuentasRepository;
    @InjectMocks
    private ClienteIntegracionRabbitRepositoryImpl rabbitRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void procesarClienteCreado() {
        var clienteExpect = Cliente.builder()
            .clienteId(123L)
            .nombre("Cliente")
            .build();
        var evento = new ClienteCreadoEvent(123L, "Cliente");
        rabbitRepository.procesarEvento(evento);
        Mockito.verify(clienteRepository).save(clienteExpect);
    }

    @Test
    void procesarClienteCreadoException(CapturedOutput output) {
        var clienteExpect = Cliente.builder()
            .clienteId(123L)
            .nombre("Cliente")
            .build();
        var evento = new ClienteCreadoEvent(123L, "Cliente");
        Mockito.doThrow(PersistenceException.class).when(clienteRepository).save(clienteExpect);
        rabbitRepository.procesarEvento(evento);
        Assertions.assertTrue(output.getOut().contains("Se ha producido un error procesando el evento de creacion"));
    }

    @Test
    void procesarClienteModificado() {
        var clienteExpect = Cliente.builder()
            .clienteId(123L)
            .nombre("Cliente Nuevo")
            .build();
        var evento = new ClienteModificadoEvent(123L, "Cliente Nuevo");
        rabbitRepository.procesarEvento(evento);
        Mockito.verify(clienteRepository).save(clienteExpect);
    }

    @Test
    void procesarClienteModificadoException(CapturedOutput output) {
        var clienteExpect = Cliente.builder()
            .clienteId(123L)
            .nombre("Cliente Nuevo")
            .build();
        var evento = new ClienteModificadoEvent(123L, "Cliente Nuevo");
        Mockito.doThrow(PersistenceException.class).when(clienteRepository).save(clienteExpect);
        rabbitRepository.procesarEvento(evento);
        Assertions.assertTrue(output.getOut().contains("Se ha producido un error procesando el evento de modificacion"));
    }

    @Test
    void procesarClienteEliminado() {
        var listaCuentas = CuentasFixture.getListaCuentaEntity();
        Mockito.when(clienteRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(ClientesFixture.getClienteEntity()));
        Mockito.when(cuentasRepository.findByCliente(ArgumentMatchers.any(Cliente.class)))
            .thenReturn(listaCuentas);
        var evento = new ClienteEliminadoEvent(123L, "Cliente");
        rabbitRepository.procesarEvento(evento);
        Mockito.verify(cuentasRepository).saveAll(listaCuentas);
        listaCuentas.forEach(cuenta ->
            Assertions.assertEquals(TipoEstado.False, cuenta.getEstado())
        );
    }

    @Test
    void procesarClienteEliminadoException(CapturedOutput output) {
        Mockito.when(clienteRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.empty());

        var evento = new ClienteEliminadoEvent(123L, "Cliente");
        rabbitRepository.procesarEvento(evento);
        Assertions.assertTrue(output.getOut().contains("Se ha producido un error procesando el evento de eliminacion"));
    }

    @Test
    void procesarEventoNoAdmitido(CapturedOutput output) {
        var evento = Mockito.mock(AbstractEvent.class);
        Mockito.when(evento.getAccion()).thenReturn("EVENTO_DESCONOCIDO");
        rabbitRepository.procesarEvento(evento);
        Assertions.assertTrue(output.getOut().contains("El evento que se ha emitido no es manejable por el servicio"));
    }

}
