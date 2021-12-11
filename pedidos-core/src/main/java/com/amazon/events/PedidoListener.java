package com.amazon.events;

import com.amazon.application.PedidoApplication;
import com.amazon.events.event.PagamentoRealizadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoListener {

    private final PedidoApplication application;

    @RabbitListener(queues = "proc_pedido_qu")
    public void onListener(String idPedido) {
        try {
            Thread.sleep(2000);
            application.processarPedido(idPedido);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new AmqpRejectAndDontRequeueException(exception);
        }
    }

    @RabbitListener(queues = "pagamento_proc_concluido_qu")
    public void onPagamentoRealizado(PagamentoRealizadoEvent event) {
        try {
            Thread.sleep(2000);
            application.processarPagamentoRealizado(event);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new AmqpRejectAndDontRequeueException(exception);
        }
    }
}
