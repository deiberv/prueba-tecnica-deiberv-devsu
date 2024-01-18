package com.devsu.ing.deiberv.ms.cuentamovimiento.config;

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
    @Value("${swagger.info.version: Versión de la API}")
    private String appVersion;
    @Value("${swagger.info.name: Nombre de la API}")
    private String apiName;
    @Value("${swagger.info.description: Descripción de la API}")
    private String apiDescripcion;
    @Value("${swagger.info.contact.name: Nombre del líder técnico}")
    private String contactName;
    @Value("${swagger.info.contact.mail: contacto@email.com}")
    private String contactEmail;

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(getInfo());
    }

    @Bean
    public ApiResponse apiResponse() {
        return new ApiResponse();
    }

    private Info getInfo() {
        return new Info()
            .title(apiName)
            .version(appVersion)
            .description(apiDescripcion)
            .summary(apiDescripcion)
            .contact(getContactInfo());
    }

    private Contact getContactInfo() {
        return new Contact()
            .name(contactName)
            .email(contactEmail);
    }
}
