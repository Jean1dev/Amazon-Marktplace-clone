package com.amazon.events;

import com.amazon.application.PedidoApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoListener {

    private final PedidoApplication application;

    @RabbitListener(queues = "proc_pedido_qu")
    public void onListener(String idPedido) {
        application.processarPedido(idPedido);
    }
}
