package com.devsu.ing.deiberv.ms.cliente.service;

import com.devsu.ing.deiberv.ms.cliente.web.vm.ClienteRequest;
import com.devsu.ing.deiberv.ms.cliente.web.vm.ClienteVm;

import java.util.List;

public interface ClienteService {

    /**
     * Obtiene el listado de clientes
     * @return {@link List<ClienteVm>}
     */
    List<ClienteVm> listarClientes();

    /**
     * Obtiene informacion del cliente indicado
     * @param clienteId {@link Long}
     * @return {@link ClienteVm}
     */
    ClienteVm buscarCliente(Long clienteId);

    /**
     * Crea un cliente
     * @param clienteRequest {@link ClienteRequest}
     * @return {@link ClienteVm}
     */
    ClienteVm crearCliente(ClienteRequest clienteRequest);

    /**
     * Actualiza la informacion del cliente indicado
     * @param clienteId {@link Long}
     * @param clienteRequest {@link ClienteRequest}
     * @return {@link ClienteVm}
     */
    ClienteVm actualizarCliente(Long clienteId, ClienteRequest clienteRequest);

    /**
     * Elimina el cliente indicado de la base de datos.
     * Se realiza una eliminacion logica
     * @param clienteId {@link Long}
     */
    void eliminarCliente(Long clienteId);
}
