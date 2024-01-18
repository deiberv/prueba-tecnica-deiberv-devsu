package com.devsu.ing.deiberv.ms.cliente.config;

import com.devsu.ing.deiberv.ms.cliente.config.props.RabbitMQProp;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    private final RabbitMQProp rabbitMQProp;

    @Bean
    public Queue queue(){
        return new Queue(rabbitMQProp.getName());
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(rabbitMQProp.getExchange());
    }
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
            .to(exchange())
            .with(rabbitMQProp.getRouting().getKey());
    }
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
