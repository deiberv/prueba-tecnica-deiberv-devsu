package com.devsu.ing.deiberv.ms.cliente.service.publisher;

import com.devsu.ing.deiberv.ms.cliente.config.props.RabbitMQProp;
import com.devsu.ing.deiberv.ms.common.events.AbstractEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * ClienteEventProducer
 */
@Service
@RequiredArgsConstructor
public class ClienteEventProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteEventProducer.class);
    private final RabbitMQProp rabbitMQProp;
    private final RabbitTemplate rabbitTemplate;

    public void publicarEvento(AbstractEvent event) {
        try {
            LOGGER.info("Publicando evento -> {}",event);
            this.rabbitTemplate.convertAndSend(rabbitMQProp.getExchange(), rabbitMQProp.getRouting().getKey(), event);
        } catch (AmqpException amqpException) {
            LOGGER.error("Se ha presentado un error publicando el evento {}, causado por {}", event, amqpException.getMessage());
        }
    }
}
