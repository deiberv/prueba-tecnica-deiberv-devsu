package com.devsu.ing.deiberv.ms.cliente.converter;

import com.devsu.ing.deiberv.ms.cliente.fixture.ClienteFixture;
import com.devsu.ing.deiberv.ms.cliente.web.vm.ClienteVm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class ClienteToClienteVmTest {
    @InjectMocks
    private ClienteToClienteVm clienteToClienteVm;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convert() {
        var clienteEntity = ClienteFixture.obtenerCliente();
        var clienteVm = clienteToClienteVm.convert(clienteEntity);
        Assertions.assertNotNull(clienteVm);
        Assertions.assertInstanceOf(ClienteVm.class, clienteVm);
    }
}
