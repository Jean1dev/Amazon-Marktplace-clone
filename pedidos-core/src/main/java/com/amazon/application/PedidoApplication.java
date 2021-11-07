package com.amazon.application;

import com.amazon.application.commands.CriarPedidoCommand;
import com.amazon.pedido.model.ItemPedido;
import com.amazon.pedido.model.Pedido;
import com.amazon.pedido.model.PedidoRepository;
import com.amazon.pedido.model.SituacaoPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.amazon.config.AMQPConstants.GERAR_PAGAMENTO_QUEUE;
import static com.amazon.config.AMQPConstants.PEDIDO_QUEUE;

@Service
@Transactional
@RequiredArgsConstructor
public class PedidoApplication {

    private final PedidoRepository repository;

    private final RabbitTemplate rabbitTemplate;

    public Pedido criarPedido(CriarPedidoCommand command) {
        Pedido build = Pedido.builder()
                .id(UUID.randomUUID().toString())
                .dataPedido(LocalDateTime.now())
                .situacaoPedido(SituacaoPedido.PEDIDO_REALIZADO)
                .usuario(command.getUsuario())
                .itens(command.getItem()
                        .stream()
                        .map(itemPedidoCommand -> ItemPedido.builder()
                                .id(UUID.randomUUID().toString())
                                .descricaoProduto(itemPedidoCommand.getDescricaoProduto())
                                .produtoReferencia(itemPedidoCommand.getProdutoReferencia())
                                .quantidade(itemPedidoCommand.getQuantidade())
                                .valorUnitario(itemPedidoCommand.getValorUnitario())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        Pedido pedido = repository.save(build);
        enviarEventos(pedido);

        return pedido;
    }

    private void enviarEventos(Pedido pedido) {
        rabbitTemplate.convertAndSend(PEDIDO_QUEUE, pedido);
        rabbitTemplate.convertAndSend(GERAR_PAGAMENTO_QUEUE, pedido);
    }
}
