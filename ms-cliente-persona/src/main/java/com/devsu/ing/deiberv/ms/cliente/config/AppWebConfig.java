package com.devsu.ing.deiberv.ms.cliente.config;

import com.devsu.ing.deiberv.ms.cliente.converter.ClienteToClienteVm;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * AppWebConfig
 */
@Configuration
public class AppWebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ClienteToClienteVm());
    }
}
