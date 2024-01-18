package com.devsu.ing.deiberv.ms.cliente.web.controller;


import com.devsu.ing.deiberv.ms.cliente.exception.EnumError;
import com.devsu.ing.deiberv.ms.cliente.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cliente.service.ClienteService;
import com.devsu.ing.deiberv.ms.cliente.web.vm.ClienteRequest;
import com.devsu.ing.deiberv.ms.cliente.web.vm.ClienteVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClienteController
 */

@Tag(name = "ClienteController", description = "Api Clientes")
@RestController
@RequestMapping(path = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(description = "Listado de clientes", tags = {"ClienteController"})
    @ApiResponse(responseCode = "200", description = "Listado de cliente")
    @ApiResponse(responseCode = "204", description = "No existen clientes, listado vacio")
    @ApiResponse(responseCode = "500", description = "Error general")
    @GetMapping()
    public List<ClienteVm> listar() {
        var listadoClientes = clienteService.listarClientes();
        if (listadoClientes.isEmpty()) {
            throw new SimpleException(EnumError.CLIENTE_NOT_FOUND, HttpStatus.NO_CONTENT.value());
        }
        return listadoClientes;
    }

    @Operation(description = "Obtiene un cliente segun su id", tags = {"ClienteController"})
    @ApiResponse(responseCode = "200", description = "Informacion obtenida de manera satisfactoria")
    @ApiResponse(responseCode = "404", description = "Cliente consultado no existe")
    @ApiResponse(responseCode = "500", description = "Error general")
    @GetMapping(value = "/ver/{clienteId}")
    public ClienteVm buscarPorId(@PathVariable Long clienteId) {
        return clienteService.buscarCliente(clienteId);
    }

    @Operation(description = "Crea un cliente", tags = {"ClienteController"})
    @ApiResponse(responseCode = "200", description = "Creacion del cliente satisfactoria")
    @ApiResponse(responseCode = "500", description = "Error general")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteVm crear(@RequestBody ClienteRequest clienteRequest) {
        return clienteService.crearCliente(clienteRequest);
    }

    @Operation(description = "Actualiza la informacion del cliente indicado", tags = {"ClienteController"})
    @ApiResponse(responseCode = "200", description = "Actualizacion de cliente satisfactoria")
    @ApiResponse(responseCode = "404", description = "Cliente a actualizar no existe")
    @ApiResponse(responseCode = "500", description = "Error general")
    @PatchMapping(value = "/{clienteId}")
    public ClienteVm actualizar(@PathVariable Long clienteId,
                                @RequestBody ClienteRequest clienteRequest)
    {
        return clienteService.actualizarCliente(clienteId, clienteRequest);
    }

    @Operation(description = "Eliina un cliente de forma logica", tags = {"ClienteController"})
    @ApiResponse(responseCode = "200", description = "Eliminacion de cliente satisfactoria")
    @ApiResponse(responseCode = "404", description = "Cliente a eliminar no existe")
    @ApiResponse(responseCode = "500", description = "Error general")
    @DeleteMapping(value = "/{clienteId}")
    public void eliminar(@PathVariable Long clienteId) {
        clienteService.eliminarCliente(clienteId);
    }

}
