package com.amazon.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.amazon.config.AMQPConstants.PRODUTO_QUEUE;


@Configuration
public class QueueConfiguration {

    private static final String EXCHANGE_NAME = "NETFLIX_EXCHANGE";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue produtoQ() {
        return new Queue(PRODUTO_QUEUE, true, false, false);
    }

    @Bean
    public Binding produto(DirectExchange exchange) {
        return BindingBuilder.bind(produtoQ())
                .to(exchange)
                .with(PRODUTO_QUEUE);
    }
}
