package com.amazon.events;

import com.amazon.application.ProdutoApplication;
import com.amazon.events.event.ProdutoCriadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoListener {

    private final ProdutoApplication application;

    @RabbitListener(queues = "produto_queue")
    public void on(ProdutoCriadoEvent event) {
        application.onCreate(event);
    }
}
