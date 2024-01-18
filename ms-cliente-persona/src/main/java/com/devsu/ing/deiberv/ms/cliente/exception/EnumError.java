package com.devsu.ing.deiberv.ms.cliente.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * EnumError.
 */
@Getter
@AllArgsConstructor
public enum EnumError {
    /*Errores genericos*/
    DEFAULT("500", "Error Generico"),
    REST_CLIENT("ERROR_CO_001", "Error al consumir servicio REST"),
    INVALID_ARGS("ERROR_CO_002", "Argumentos invalidos"),
    NOT_ALLOWED("ERROR_CO_003", "No permitido"),
    INVALID_BODY("ERROR_CO_004", "Cuerpo de llamada invalido"),
    NO_CONTENT("ERROR_CO_005", "Cuerpo de llamada invalido"),
    /*Errores de dominio - Cliente*/
    CLIENTE_NOT_FOUND("ERROR_CL_001", "Cliente no existe"),;

    private String code;
    private String message;
}
