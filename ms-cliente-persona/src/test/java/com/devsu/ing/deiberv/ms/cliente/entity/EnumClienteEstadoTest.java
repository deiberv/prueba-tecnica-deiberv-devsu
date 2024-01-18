package com.devsu.ing.deiberv.ms.cliente.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class EnumClienteEstadoTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void estadoString() {
        var resultado = EnumClienteEstado.getEstadoString(EnumClienteEstado.TRUE);
        Assertions.assertEquals("True", resultado);
    }

    @Test
    void estadoString2() {
        var resultado = EnumClienteEstado.getEstadoString(EnumClienteEstado.FALSE);
        Assertions.assertEquals("False", resultado);
    }
}
