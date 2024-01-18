package com.devsu.ing.deiberv.ms.cuentamovimiento.exception;

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
    NO_CONTENT("ERROR_CO_005", "No existen datos"),
    /*Errores de dominio - Cuenta*/
    CUENTA_NOT_FOUND("ERROR_CTA_001", "La cuenta indicada no existe"),
    CREAR_CUENTA_EXISTENTE("ERROR_CTA_002", "La cuenta que intenta crear ya existe"),
    CREAR_CUENTA_SALDO_INVALIDO("ERROR_CTA_003", "El saldo inicial de la cuenta no puede ser menor que cero"),
    ERROR_CONSULTA_CLIENTE("ERROR_CTA_004", "Error consultando informacion del cliente"),
    CREAR_CUENTA_CLIENTE_NOT_FOUND("ERROR_CTA_005", "No se ha obtenido informacion del cliente"),
    /*Errores de dominio - Movimientos*/
    SALDO_INSUFICIENTE("ERROR_MOV_001", "Saldo no disponible"),
    ERROR_FECHA_INVALIDA("ERROR_MOV_002", "La fecha de consulta no puede ser superior a la fecha del dia"),
    ERROR_FECHA_FIN_INVALIDA("ERROR_MOV_003", "La fecha de fin de la consulta no puede ser superior a la fecha de inicio");

    private String code;
    private String message;

}
