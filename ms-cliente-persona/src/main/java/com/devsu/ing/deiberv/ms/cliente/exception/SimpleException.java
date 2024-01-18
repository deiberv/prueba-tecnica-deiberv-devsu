package com.devsu.ing.deiberv.ms.cliente.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

/**
 * SimpleException.
 */
@Getter
@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed", "status", "errorEnum"})
public class SimpleException extends RuntimeException {
    private final int status;
    private final String code;
    private final EnumError errorEnum;

    public SimpleException(final EnumError enumError) {
        this(enumError, (Throwable)null);
    }

    public SimpleException(final EnumError enumError, final Throwable cause) {
        this(enumError, 500, cause);
    }

    public SimpleException(final EnumError enumError, final int httpStatus) {
        this(enumError, httpStatus, (Throwable)null);
    }

    public SimpleException(final EnumError enumError, final int httpStatus, final Throwable cause) {
        super(enumError.getMessage(), cause);
        this.errorEnum = enumError;
        this.status = httpStatus;
        this.code = enumError.getCode();
    }
}
