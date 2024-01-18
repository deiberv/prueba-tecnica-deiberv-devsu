package com.devsu.ing.deiberv.ms.cliente.fixture;

import com.devsu.ing.deiberv.ms.cliente.entity.Cliente;
import com.devsu.ing.deiberv.ms.cliente.entity.EnumClienteEstado;
import com.devsu.ing.deiberv.ms.cliente.entity.EnumGenero;
import com.devsu.ing.deiberv.ms.cliente.web.vm.ClienteRequest;
import com.devsu.ing.deiberv.ms.cliente.web.vm.ClienteVm;

import java.util.List;

/**
 * ClienteFixture
 */
public class ClienteFixture {

    public static List<Cliente> obtenerListaClienteEntity() {
        return List.of(Cliente.builder()
                .clienteId(123L)
                .nombre("Nombre Cliente")
                .estado(EnumClienteEstado.TRUE)
                .direccion("Direccion")
                .edad(39L)
                .genero(EnumGenero.MASCULINO)
                .password("123456")
            .build());
    }

    public static List<Cliente> obtenerListaClienteEntityVacia() {
        return List.of();
    }

    public static Cliente obtenerCliente() {
        return Cliente.builder()
            .clienteId(123L)
            .nombre("Nombre Cliente")
            .estado(EnumClienteEstado.TRUE)
            .direccion("Direccion")
            .edad(39L)
            .genero(EnumGenero.FEMENINO)
            .password("123456")
            .identificacion("E-14596523")
            .telefono("74859632")
            .build();
    }

    public static ClienteRequest obtenerClienteRequest() {
        return ClienteRequest.builder()
            .nombre("Nombre Cliente")
            .genero(EnumGenero.OTRO)
            .edad(39L)
            .identificacion("E-14596523")
            .direccion("Direccion")
            .telefono("74859632")
            .password("123456")
            .build();
    }

    public static List<ClienteVm> obtenerListadoClienteVm() {
        return List.of(obtenerClienteVm());
    }

    public static ClienteVm obtenerClienteVm() {
        return ClienteVm.builder()
            .clienteId(123L)
            .nombre("Nombre Cliente")
            .genero(EnumGenero.MASCULINO.name())
            .edad(39L)
            .identificacion("E-14596523")
            .direccion("Direccion")
            .telefono("74859632")
            .estado(EnumClienteEstado.getEstadoString(EnumClienteEstado.TRUE))
            .build();
    }
}
