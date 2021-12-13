package com.amazon.application;

import com.amazon.events.event.PagamentoProcessadoEvent;
import com.amazon.events.event.PedidoEvent;
import com.amazon.events.event.SituacaoPagamento;
import com.amazon.model.Pagamento;
import com.amazon.model.PagamentoRepository;
import com.amazon.service.PagamentoException;
import com.amazon.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.amazon.config.AMQPConstants.PAGAMENTO_PROCESSADO_QUEUE;

@Service
@Transactional
@AllArgsConstructor
public class PagamentoApplication {

    private final PagamentoRepository repository;

    private final PagamentoService pagamentoService;

    private final RabbitTemplate rabbitTemplate;

    public void efetuarPagamento(PedidoEvent event) {
        Pagamento pagamento = Pagamento.builder()
                .id(UUID.randomUUID().toString())
                .dataHoraPagamento(LocalDateTime.now())
                .pedidoRef(event.getId())
                .usuario(event.getUsuario())
                .valorTotal(event.getValorTotal())
                .build();

        try {
            pagamentoService.efetuarCobranca();
            repository.save(pagamento);
            PagamentoProcessadoEvent processadoEvent = PagamentoProcessadoEvent.builder()
                    .situacaoPagamento(SituacaoPagamento.PAGAMENTO_REALIZADO)
                    .valorTotal(pagamento.getValorTotal())
                    .idPedido(pagamento.getPedidoRef())
                    .dataPagamento(pagamento.getDataHoraPagamento())
                    .build();

            rabbitTemplate.convertAndSend(PAGAMENTO_PROCESSADO_QUEUE, processadoEvent);
        } catch (PagamentoException e) {
            e.printStackTrace();

            PagamentoProcessadoEvent processadoEvent = PagamentoProcessadoEvent.builder()
                    .situacaoPagamento(SituacaoPagamento.FALHA_NO_PAGAMENTO)
                    .valorTotal(pagamento.getValorTotal())
                    .idPedido(pagamento.getPedidoRef())
                    .dataPagamento(pagamento.getDataHoraPagamento())
                    .build();

            rabbitTemplate.convertAndSend(PAGAMENTO_PROCESSADO_QUEUE, processadoEvent);
        }
    }
}
