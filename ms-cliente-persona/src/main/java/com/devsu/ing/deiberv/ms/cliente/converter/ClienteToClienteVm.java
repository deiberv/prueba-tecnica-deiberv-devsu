package com.devsu.ing.deiberv.ms.cliente.converter;

import com.devsu.ing.deiberv.ms.cliente.entity.Cliente;
import com.devsu.ing.deiberv.ms.cliente.entity.EnumClienteEstado;
import com.devsu.ing.deiberv.ms.cliente.web.vm.ClienteVm;
import org.springframework.core.convert.converter.Converter;

/**
 * ClienteToClienteVm
 */

public class ClienteToClienteVm implements Converter<Cliente, ClienteVm> {
    @Override
    public ClienteVm convert(Cliente source) {
        return ClienteVm.builder()
            .clienteId(source.getClienteId())
            .nombre(source.getNombre())
            .genero(source.getGenero().name())
            .edad(source.getEdad())
            .identificacion(source.getIdentificacion())
            .direccion(source.getDireccion())
            .telefono(source.getTelefono())
            .estado(EnumClienteEstado.getEstadoString(source.getEstado()))
        .build();
    }
}
