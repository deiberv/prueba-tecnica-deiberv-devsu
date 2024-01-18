package com.devsu.ing.deiberv.ms.cuentamovimiento.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQConfig
 */
@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }


}
