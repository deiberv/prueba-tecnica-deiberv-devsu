package com.devsu.ing.deiberv.ms.cuentamovimiento.config;

import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.EnumError;
import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.SimpleException;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestExceptionHandlerTest {

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;
    @Mock
    private WebRequest webRequest;
    private final HttpHeaders httpHeaders = HttpHeaders.EMPTY;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void handleMethodArgumentNotValid() {
        var exception = Mockito.mock(MethodArgumentNotValidException.class);
        var response = restExceptionHandler.handleMethodArgumentNotValid(exception, httpHeaders,
            HttpStatus.BAD_REQUEST, webRequest);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void handleHttpRequestMethodNotSupported() {
        var exception = Mockito.mock(HttpRequestMethodNotSupportedException.class);
        var response = restExceptionHandler.handleHttpRequestMethodNotSupported(exception, httpHeaders,
            HttpStatus.METHOD_NOT_ALLOWED, webRequest);
        assertNotNull(response);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    void handleServletRequestBindingException() {
        var exception = Mockito.mock(ServletRequestBindingException.class);
        var response = restExceptionHandler.handleServletRequestBindingException(exception, httpHeaders,
            HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    void handleHttpMessageNotReadable() {
        var exception = Mockito.mock(HttpMessageNotReadableException.class);
        var response = restExceptionHandler.handleHttpMessageNotReadable(exception, httpHeaders,
            HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    void handleSimpleException(){
        var exception = new SimpleException(EnumError.DEFAULT);
        var response = restExceptionHandler.handleSimpleException(exception, webRequest);
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    void handleSimpleExceptionRestClientResponseException(){
        var restClientException = new BadRequestException();
        var exception = new SimpleException(EnumError.REST_CLIENT, restClientException);
        var response = restExceptionHandler.handleSimpleException(exception, webRequest);
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    void handleGeneralException() {
        var exception = Mockito.mock(HttpMessageNotReadableException.class);
        var response = restExceptionHandler.handleGeneralException(exception, webRequest);
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(SimpleException.class, response.getBody());

    }
}

