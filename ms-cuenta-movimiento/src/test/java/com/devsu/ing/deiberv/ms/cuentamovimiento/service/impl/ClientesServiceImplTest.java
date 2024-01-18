package com.devsu.ing.deiberv.ms.cuentamovimiento.service.impl;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuentamovimiento.fixture.ClientesFixture;
import com.devsu.ing.deiberv.ms.cuentamovimiento.repository.ClienteRepository;
import com.devsu.ing.deiberv.ms.cuentamovimiento.restclients.ClienteRestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class ClientesServiceImplTest {

    @InjectMocks
    private ClientesServiceImpl clientesService;
    @Mock
    private ClienteRestClient restClient;
    @Mock
    private ClienteRepository clienteRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarCliente() {
        Mockito.when(clienteRepository.findById(ArgumentMatchers.any(Long.class)))
            .thenReturn(Optional.of(ClientesFixture.getClienteEntity()));
        var resultado = clientesService.buscarCliente(123L);
        Assertions.assertNotNull(resultado);
    }

    @Test
    void buscarClienteMs() {
        var cliente = Cliente.builder().clienteId(123L).nombre("Cliente").build();
        Mockito.when(restClient.buscarClientePorId(ArgumentMatchers.any(Long.class)))
            .thenReturn(ClientesFixture.getClienteRs());
        Mockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class)))
            .thenReturn(cliente);
        var resultado = clientesService.buscarClienteMs(123L);
        Mockito.verify(clienteRepository).save(cliente);
        Assertions.assertNotNull(resultado);
        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals(cliente, resultado.get());
    }

}
