package com.devsu.ing.deiberv.ms.cuentamovimiento.web.controller;

import com.devsu.ing.deiberv.ms.cuentamovimiento.service.MovimientosService;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.MovimientoRequest;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.MovimientoRpt;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.MovimientoSaldo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * MovimientosController
 */
@RestController
@RequestMapping(path = "/movimientos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MovimientosController {

    private final MovimientosService movimientosService;

    @Operation(description = "Registra un movimeinto")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Movimiento registrado"),
        @ApiResponse(responseCode = "400", description = "Peticion invalida"),
        @ApiResponse(responseCode = "500", description = "Error general"),
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoSaldo registrarMovimiento(@RequestBody MovimientoRequest movimientoRequest) {
        return this.movimientosService.registrar(movimientoRequest);
    }

    @GetMapping(value="/cliente/{clienteId}")
    public List<MovimientoRpt> reporte(@PathVariable Long clienteId,
                                       @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaInicial,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaFin)
    {
        return this.movimientosService.reporte(clienteId, fechaInicial, fechaFin);
    }

}
