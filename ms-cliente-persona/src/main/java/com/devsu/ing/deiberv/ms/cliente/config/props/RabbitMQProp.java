package com.devsu.ing.deiberv.ms.cliente.config.props;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * RabbitMQProp
 */
@Getter
@Setter
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "rabbitmq.queue")
public class RabbitMQProp {

    private String name;
    private String exchange;
    private Routing routing;
    @Getter
    @Setter
    public static class Routing {
        private String key;
    }
}
