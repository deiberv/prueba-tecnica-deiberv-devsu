package com.devsu.ing.deiberv.ms.cliente.service.impl;

import com.devsu.ing.deiberv.ms.cliente.entity.Cliente;
import com.devsu.ing.deiberv.ms.cliente.entity.EnumClienteEstado;
import com.devsu.ing.deiberv.ms.cliente.exception.EnumError;
import com.devsu.ing.deiberv.ms.cliente.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cliente.repository.ClienteRepository;
import com.devsu.ing.deiberv.ms.cliente.service.ClienteService;
import com.devsu.ing.deiberv.ms.cliente.service.publisher.ClienteEventProducer;
import com.devsu.ing.deiberv.ms.cliente.web.vm.ClienteRequest;
import com.devsu.ing.deiberv.ms.cliente.web.vm.ClienteVm;
import com.devsu.ing.deiberv.ms.common.events.cliente.ClienteCreadoEvent;
import com.devsu.ing.deiberv.ms.common.events.cliente.ClienteEliminadoEvent;
import com.devsu.ing.deiberv.ms.common.events.cliente.ClienteModificadoEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * ClienteServiceImpl
 */
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteServiceImpl.class);
    private final ClienteRepository clienteRepository;
    private final ConversionService conversionService;
    private final ClienteEventProducer clienteEventProducer;
    @Override
    public List<ClienteVm> listarClientes() {
        return this.clienteRepository.findAll(Sort.by(Sort.Order.by("nombre")))
            .stream().map(this::convertToClienteVm)
            .toList();
    }

    @Override
    public ClienteVm buscarCliente(Long clienteId) {
        LOGGER.debug("Buscando información del cliente {}", clienteId);
        var cliente = this.findById(clienteId);
        return this.convertToClienteVm(cliente);
    }

    @Override
    @Transactional
    public ClienteVm crearCliente(ClienteRequest clienteRequest) {
        LOGGER.debug("Creando cliente {}", clienteRequest);
        var cliente = Cliente.builder()
                .nombre(clienteRequest.getNombre())
                .genero(clienteRequest.getGenero())
                .edad(clienteRequest.getEdad())
                .identificacion(clienteRequest.getIdentificacion())
                .direccion(clienteRequest.getDireccion())
                .telefono(clienteRequest.getTelefono())
                .password(clienteRequest.getPassword())
                .estado(clienteRequest.getEstado())
                .build();
        this.clienteRepository.save(cliente);
        this.clienteEventProducer.publicarEvento(new ClienteCreadoEvent(cliente.getClienteId(), cliente.getNombre()));
        return this.convertToClienteVm(cliente);
    }

    @Override
    @Transactional
    public ClienteVm actualizarCliente(Long clienteId, ClienteRequest clienteRequest) {
        LOGGER.debug("Actualizando datos del cliente {} nuevos datos {}", clienteId, clienteRequest);
        var cliente = this.findById(clienteId);
        cliente.setNombre(Objects.requireNonNullElse(clienteRequest.getNombre(), cliente.getNombre()));
        cliente.setGenero(Objects.requireNonNullElse(clienteRequest.getGenero(), cliente.getGenero()));
        cliente.setEdad(Objects.requireNonNullElse(clienteRequest.getEdad(), cliente.getEdad()));
        cliente.setIdentificacion(Objects.requireNonNullElse(clienteRequest.getIdentificacion(), cliente.getIdentificacion()));
        cliente.setDireccion(Objects.requireNonNullElse(clienteRequest.getDireccion(), cliente.getDireccion()));
        cliente.setTelefono(Objects.requireNonNullElse(clienteRequest.getTelefono(), cliente.getTelefono()));
        cliente.setPassword(Objects.requireNonNullElse(clienteRequest.getPassword(), cliente.getPassword()));
        this.clienteRepository.save(cliente);
        this.clienteEventProducer.publicarEvento(new ClienteModificadoEvent(cliente.getClienteId(), cliente.getNombre()));
        return this.convertToClienteVm(cliente);
    }

    @Override
    @Transactional
    public void eliminarCliente(Long clienteId) {
        LOGGER.debug("Eliminando de manera logica al cliente {}", clienteId);
        var cliente = this.findById(clienteId);
        cliente.setEstado(EnumClienteEstado.FALSE);
        this.clienteRepository.save(cliente);
        this.clienteEventProducer.publicarEvento(new ClienteEliminadoEvent(cliente.getClienteId(), cliente.getNombre()));
    }

    //---------------------------
    //- Metodos privados --------
    //---------------------------
    private Cliente findById(Long clienteId) {
        return this.clienteRepository.findById(clienteId)
                .orElseThrow(() -> new SimpleException(EnumError.CLIENTE_NOT_FOUND, HttpStatus.NOT_FOUND.value()));
    }

    private ClienteVm convertToClienteVm(Cliente cliente) {
        return this.conversionService.convert(cliente, ClienteVm.class);
    }

}
