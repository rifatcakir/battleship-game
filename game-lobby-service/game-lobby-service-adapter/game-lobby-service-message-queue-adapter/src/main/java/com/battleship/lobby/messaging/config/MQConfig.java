package com.battleship.lobby.messaging.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${battleship.rabbitmq.queue}")
    private String queue;
    @Value("${battleship.rabbitmq.deadletter.queue}")
    private String deadLetterQueue;

    @Value("${battleship.rabbitmq.exchange}")
    private String exchange;
    @Value("${battleship.rabbitmq.deadletter.exchange}")
    private String deadLetterExchange;

    @Value("${battleship.rabbitmq.routingkey}")
    private String routingKey;
    @Value("${battleship.rabbitmq.deadletter.routingkey}")
    private String deadLetterRoutingKey;

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchange);
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable(deadLetterQueue).build();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(queue).withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadLetterRoutingKey).build();
    }

    @Bean
    Binding deadLetterQueuebinding() {
        return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with(deadLetterRoutingKey);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(routingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

}