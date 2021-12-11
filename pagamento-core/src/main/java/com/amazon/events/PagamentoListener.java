package com.amazon.events;

import com.amazon.application.PagamentoApplication;
import com.amazon.events.event.PedidoEvent;
import lombok.AllArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PagamentoListener {

    private final PagamentoApplication pagamentoApplication;

    @RabbitListener(queues = "gerar_pagamento_qu")
    public void onListener(PedidoEvent event) {
        try {
            pagamentoApplication.efetuarPagamento(event);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new AmqpRejectAndDontRequeueException(exception);
        }
    }
}
