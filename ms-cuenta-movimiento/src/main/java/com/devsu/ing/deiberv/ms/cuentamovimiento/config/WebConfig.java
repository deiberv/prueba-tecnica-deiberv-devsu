package com.devsu.ing.deiberv.ms.cuentamovimiento.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * WebConfig
 */
@Configuration
public class WebConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
            .build();
    }

}
