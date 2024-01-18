package com.devsu.ing.deiberv.ms.cuentamovimiento.service.impl;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.EnumError;
import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuentamovimiento.repository.ClienteRepository;
import com.devsu.ing.deiberv.ms.cuentamovimiento.restclients.ClienteRestClient;
import com.devsu.ing.deiberv.ms.cuentamovimiento.service.ClientesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * ClientesServiceImpl
 */
@Service
@RequiredArgsConstructor
public class ClientesServiceImpl implements ClientesService {
    private final ClienteRestClient restClient;
    private final ClienteRepository clienteRepository;
    @Override
    @Transactional(readOnly = true)
    public Cliente buscarCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
            .or(() -> this.buscarClienteMs(clienteId))
            .orElseThrow(() -> new SimpleException(EnumError.CREAR_CUENTA_CLIENTE_NOT_FOUND));
    }
    @Transactional()
    public Optional<Cliente> buscarClienteMs(Long clienteId) {
        var clienteRs = this.restClient.buscarClientePorId(clienteId);
        var clienteEntity = Cliente.builder()
            .clienteId(clienteRs.getClienteId())
            .nombre(clienteRs.getNombre())
            .build();
        this.clienteRepository.save(clienteEntity);
        return Optional.of(clienteEntity);
    }
}
