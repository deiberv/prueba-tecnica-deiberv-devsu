package com.devsu.ing.deiberv.ms.cliente.service.impl;

import com.devsu.ing.deiberv.ms.cliente.entity.Cliente;
import com.devsu.ing.deiberv.ms.cliente.entity.EnumClienteEstado;
import com.devsu.ing.deiberv.ms.cliente.entity.EnumGenero;
import com.devsu.ing.deiberv.ms.cliente.exception.EnumError;
import com.devsu.ing.deiberv.ms.cliente.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cliente.fixture.ClienteFixture;
import com.devsu.ing.deiberv.ms.cliente.repository.ClienteRepository;
import com.devsu.ing.deiberv.ms.cliente.service.publisher.ClienteEventProducer;
import com.devsu.ing.deiberv.ms.cliente.web.vm.ClienteRequest;
import com.devsu.ing.deiberv.ms.cliente.web.vm.ClienteVm;
import com.devsu.ing.deiberv.ms.common.events.cliente.ClienteCreadoEvent;
import com.devsu.ing.deiberv.ms.common.events.cliente.ClienteEliminadoEvent;
import com.devsu.ing.deiberv.ms.common.events.cliente.ClienteModificadoEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClienteServiceImplTest {

    @InjectMocks
    private ClienteServiceImpl clienteService;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ConversionService conversionService;
    @Mock
    private ClienteEventProducer clienteEventProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarClientesVacia() {
        var clienteEntity = Cliente.builder().build();
        when(clienteRepository.findAll(any(Sort.class)))
            .thenReturn(ClienteFixture.obtenerListaClienteEntityVacia());
        var listado = clienteService.listarClientes();
        assertNotNull(listado);
        assertTrue(listado.isEmpty());
        verify(conversionService,never()).convert(clienteEntity, ClienteVm.class);
    }

    @Test
    void listarClientes() {
        var clienteEntity = Cliente.builder().build();
        var clienteVm = ClienteVm.builder().build();
        var lstaClientes = ClienteFixture.obtenerListaClienteEntity();
        when(clienteRepository.findAll(any(Sort.class)))
            .thenReturn(lstaClientes);
        when(conversionService.convert(clienteEntity, ClienteVm.class)).thenReturn(clienteVm);
        var listado = clienteService.listarClientes();
        assertNotNull(listado);
        assertFalse(listado.isEmpty());
    }

    @Test
    void buscarClienteExistente() {
        var expectResponse = ClienteFixture.obtenerCliente();
        var clienteVm = ClienteVm.builder().build();
        when(clienteRepository.findById(any(Long.class)))
            .thenReturn(Optional.of(expectResponse));
        when(conversionService.convert(expectResponse, ClienteVm.class)).thenReturn(clienteVm);
        var resultado = clienteService.buscarCliente(123L);
        assertNotNull(resultado);
        assertInstanceOf(ClienteVm.class, resultado);
    }

    @Test
    void buscarClienteNoExistente() {
        when(clienteRepository.findById(any(Long.class)))
            .thenReturn(Optional.empty());
        var throwable = assertThrows(SimpleException.class, () -> clienteService.buscarCliente(98745L));
        Assertions.assertEquals(EnumError.CLIENTE_NOT_FOUND, throwable.getErrorEnum());
    }

    @Test
    void crearCliente() {
        var clienteRequest = ClienteFixture.obtenerClienteRequest();
        var clienteVm = ClienteVm.builder().build();
        var clienteEntity = Cliente.builder()
            .clienteId(123L)
            .nombre(clienteRequest.getNombre())
            .genero(clienteRequest.getGenero())
            .edad(clienteRequest.getEdad())
            .identificacion(clienteRequest.getIdentificacion())
            .direccion(clienteRequest.getDireccion())
            .telefono(clienteRequest.getTelefono())
            .password(clienteRequest.getPassword())
            .estado(EnumClienteEstado.TRUE)
            .build();

        when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenReturn(clienteEntity);
        when(conversionService.convert(clienteEntity, ClienteVm.class)).thenReturn(clienteVm);
        doNothing().when(clienteEventProducer).publicarEvento(any(ClienteCreadoEvent.class));
        clienteService.crearCliente(clienteRequest);
        verify(clienteRepository,times(1)).save(any(Cliente.class));
        verify(clienteEventProducer,times(1)).publicarEvento(any(ClienteCreadoEvent.class));
    }

    @ParameterizedTest
    @MethodSource("provideClienteRequest")
    void actualizarCliente(ClienteRequest clienteRequest) {
        var expectResponse = ClienteFixture.obtenerCliente();
        var clienteVm = ClienteVm.builder().build();
        when(clienteRepository.findById(any(Long.class)))
            .thenReturn(Optional.of(expectResponse));
        when(conversionService.convert(expectResponse, ClienteVm.class)).thenReturn(clienteVm);
        doNothing().when(clienteEventProducer).publicarEvento(any(ClienteModificadoEvent.class));
        var resultado = clienteService.actualizarCliente(98745L, clienteRequest);
        assertNotNull(resultado);
        verify(clienteRepository,times(1)).save(any(Cliente.class));
        verify(clienteEventProducer,times(1)).publicarEvento(any(ClienteModificadoEvent.class));
    }

    @Test
    void actualizarClienteNoExiste() {
        var clienteRequest = ClienteRequest.builder().build();
        when(clienteRepository.findById(any(Long.class)))
            .thenReturn(Optional.empty());
        var throwable = assertThrows(SimpleException.class,
            () -> clienteService.actualizarCliente(98745L, clienteRequest));
        Assertions.assertEquals(EnumError.CLIENTE_NOT_FOUND, throwable.getErrorEnum());
        verify(clienteEventProducer,never()).publicarEvento(any(ClienteModificadoEvent.class));
    }

    @Test
    void eliminarCliente() {
        var expectResponse = ClienteFixture.obtenerCliente();
        when(clienteRepository.findById(any(Long.class)))
            .thenReturn(Optional.of(expectResponse));
        doNothing().when(clienteEventProducer).publicarEvento(any(ClienteModificadoEvent.class));
        this.clienteService.eliminarCliente(123L);
        expectResponse.setEstado(EnumClienteEstado.FALSE);
        verify(this.clienteRepository, times(1)).save(expectResponse);
        verify(clienteEventProducer,times(1)).publicarEvento(any(ClienteEliminadoEvent.class));
    }

    @Test
    void eliminarClienteNoExiste() {
        when(clienteRepository.findById(any(Long.class)))
            .thenReturn(Optional.empty());
        var throwable = assertThrows(SimpleException.class, () -> clienteService.eliminarCliente(98745L));
        Assertions.assertEquals(EnumError.CLIENTE_NOT_FOUND, throwable.getErrorEnum());
        verify(clienteEventProducer,never()).publicarEvento(any(ClienteEliminadoEvent.class));
    }

    private static Stream<Arguments> provideClienteRequest() {
        return Stream.of(
            Arguments.of(ClienteRequest.builder().build()),
            Arguments.of(ClienteRequest.builder()
                    .nombre("Nombre Actualizado")
                    .genero(EnumGenero.MASCULINO)
                    .edad(40L)
                    .identificacion("V-15748965")
                    .direccion("Direccion Actualizada")
                    .telefono("098254785")
                    .password("12345678")
                .build())
        );
    }
}
