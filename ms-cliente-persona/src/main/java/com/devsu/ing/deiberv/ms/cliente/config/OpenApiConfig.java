/*
 * @(#)OpenApiConfig.java
 *
 * Copyright (c) BANCO DE CHILE (Chile). All rights reserved.
 *
 * All rights to this product are owned by BANCO DE CHILE and may only
 * be used under the terms of its associated license document. You may NOT
 * copy, modify, sublicense, or distribute this source file or portions of
 * it unless previously authorized in writing by BANCO DE CHILE.
 * In any event, this notice and the above copyright must always be included
 * verbatim with this file.
 */
package com.devsu.ing.deiberv.ms.cliente.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApiConfig.
 */
@Configuration
public class OpenApiConfig {
    @Value("${swagger.info.version: Versión de la API}") String appVersion;
    @Value("${swagger.info.name: Nombre de la API}") String apiName;
    @Value("${swagger.info.description: Descripción de la API}") String apiDescripcion;
    @Value("${swagger.info.contact.name: Nombre del líder técnico}") String contactName;
    @Value("${swagger.info.contact.mail: contacto@email.com}") String contactEmail;

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title(apiName)
                        .version(appVersion)
                        .description(apiDescripcion)
                        .contact(new Contact()
                                .name(contactName)
                                .email(contactEmail)));
    }

    @Bean
    public ApiResponse apiResponse() {
        return new ApiResponse();
    }
}
