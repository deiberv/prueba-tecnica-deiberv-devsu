package com.devsu.ing.deiberv.ms.cuentamovimiento.restclients;

import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.EnumError;
import com.devsu.ing.deiberv.ms.cuentamovimiento.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuentamovimiento.restclients.dtos.ClienteRs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

/**
 * ClienteRestClient
 */
@Component
@RequiredArgsConstructor
public class ClienteRestClient {

    private final RestClient restClient;
    @Value("${rest.client.servicio.cliente.endpoint}")
    private String msClientesEndPoint;

    public ClienteRs buscarClientePorId(Long clienteId) {
        try {
            return this.restClient.get()
                .uri(this.msClientesEndPoint.concat("/ver/{clienteId}"), clienteId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ClienteRs.class);
        } catch (RestClientException exc) {
            throw new SimpleException(EnumError.ERROR_CONSULTA_CLIENTE, exc);
        }
    }
}
