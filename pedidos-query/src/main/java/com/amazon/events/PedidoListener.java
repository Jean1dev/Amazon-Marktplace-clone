package com.amazon.events;

import com.amazon.application.PedidoApplication;
import com.amazon.events.event.PedidoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoListener {

    private final PedidoApplication application;

    @RabbitListener(queues = "pedido_criado_qu")
    public void onListening(PedidoEvent pedidoEvent) {
        application.onCreate(pedidoEvent);
    }
}
