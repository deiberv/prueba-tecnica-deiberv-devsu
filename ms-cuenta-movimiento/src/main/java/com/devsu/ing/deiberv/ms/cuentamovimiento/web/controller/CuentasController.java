package com.devsu.ing.deiberv.ms.cuentamovimiento.web.controller;

import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.EnumError;
import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuentamovimiento.service.CuentasService;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.CuentaRequest;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.CuentaVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * CuentasController
 */
@RestController
@RequestMapping(path = "/cuentas", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CuentasController {

    private final CuentasService cuentaService;

    @Operation(description = "Listado de cuentas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de cuentas"),
        @ApiResponse(responseCode = "204", description = "No existen cuentas, listado vacio"),
        @ApiResponse(responseCode = "500", description = "Error general"),
    })
    @GetMapping()
    public Page<CuentaVm> listar(Pageable pageable){
        var listadoCuentas = this.cuentaService.listarCuentas(pageable);
        if (!listadoCuentas.hasContent()) {
            throw new SimpleException(EnumError.NO_CONTENT, HttpStatus.NO_CONTENT.value());
        }
        return listadoCuentas;
    }

    @Operation(description = "Obtiene detalle de la cuenta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Informacion obtenida de manera satisfactoria"),
        @ApiResponse(responseCode = "404", description = "Cuenta no existente"),
        @ApiResponse(responseCode = "500", description = "Error general"),
    })
    @GetMapping(value = "/detalle/{numeroCuenta}")
    public CuentaVm buscarPorId(@PathVariable String numeroCuenta) {
        return this.cuentaService.buscarCuenta(numeroCuenta);
    }

    @Operation(description = "Crea una cuenta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Creacion del clienteVm satisfactoria"),
        @ApiResponse(responseCode = "500", description = "Error general"),
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaVm crear(@RequestBody @Valid CuentaRequest cuentaRequest) {
        return this.cuentaService.crearCuenta(cuentaRequest);
    }

    @Operation(description = "Eliina una cuenta de forma logica")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminacion de clienteVm satisfactoria"),
        @ApiResponse(responseCode = "404", description = "ClienteVm a eliminar no existe"),
        @ApiResponse(responseCode = "500", description = "Error general"),
    })
    @DeleteMapping(value = "/{numeroCuenta}")
    public void eliminar(@PathVariable String numeroCuenta) {
        this.cuentaService.eliminarCuenta(numeroCuenta);
    }

}
