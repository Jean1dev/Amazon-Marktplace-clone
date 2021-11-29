package com.amazon.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.amazon.config.AMQPConstants.*;

@Configuration
public class QueueConfiguration {

    private static final String EXCHANGE_NAME = "NETFLIX_EXCHANGE";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue pedidoQ() {
        return new Queue(PEDIDO_QUEUE, true, false, false);
    }

    @Bean
    public Queue pagamentoQ() {
        return new Queue(GERAR_PAGAMENTO_QUEUE, true, false, false);
    }

    @Bean
    public Queue procPedidoQ() {
        return new Queue(PROCESSAMENTO_PEDIDO_QUEUE, true, false, false);
    }

    @Bean
    public Binding processamentoPedido(DirectExchange exchange) {
        return BindingBuilder.bind(pedidoQ())
                .to(exchange)
                .with(PROCESSAMENTO_PEDIDO_QUEUE);
    }

    @Bean
    public Binding gerarPagamento(DirectExchange exchange) {
        return BindingBuilder.bind(pedidoQ())
                .to(exchange)
                .with(GERAR_PAGAMENTO_QUEUE);
    }

    @Bean
    public Binding pedido(DirectExchange exchange) {
        return BindingBuilder.bind(pedidoQ())
                .to(exchange)
                .with(PEDIDO_QUEUE);
    }
}
