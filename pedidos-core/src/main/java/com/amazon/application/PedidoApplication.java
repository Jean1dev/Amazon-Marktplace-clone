package com.amazon.application;

import com.amazon.application.commands.CriarPedidoCommand;
import com.amazon.events.event.PagamentoRealizadoEvent;
import com.amazon.http.ProdutoCoreRequest;
import com.amazon.http.ProdutoQueryRequest;
import com.amazon.http.dto.ProdutoDto;
import com.amazon.pedido.model.ItemPedido;
import com.amazon.pedido.model.Pedido;
import com.amazon.pedido.model.PedidoRepository;
import com.amazon.pedido.model.SituacaoPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.amazon.config.AMQPConstants.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PedidoApplication {

    private final PedidoRepository repository;

    private final RabbitTemplate rabbitTemplate;

    private final ProdutoQueryRequest produtoQueryRequest;

    private final ProdutoCoreRequest produtoCoreRequest;

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
        rabbitTemplate.convertAndSend(PROCESSAMENTO_PEDIDO_QUEUE, pedido.getId());
    }

    public void processarPedido(String idPedido) {
        Pedido pedido = repository.findById(idPedido).orElseThrow();

        pedido.getItens().forEach(itemPedido -> {
            ProdutoDto produto = produtoQueryRequest.buscarProduto(itemPedido.getProdutoReferencia());
            if (itemPedido.getQuantidade() <= produto.getQuantidadeEstoqueAtual() && !(produto.getQuantidadeEstoqueReservado() >= itemPedido.getQuantidade()))
                return;

            throw new ValidationException("Pedido não pode ser feito porque falta itens no estoque");
        });

        pedido.getItens().forEach(itemPedido -> {
            produtoCoreRequest.solicitarReservaDeEstoque(itemPedido.getProdutoReferencia(), itemPedido.getQuantidade());
        });

        rabbitTemplate.convertAndSend(GERAR_PAGAMENTO_QUEUE, pedido);
    }

    public void processarPagamentoRealizado(PagamentoRealizadoEvent event) {
        Pedido pedido = repository.findById(event.getIdPedido()).orElseThrow();

        if (event.getSituacaoPagamento() == "PAGAMENTO_REALIZADO") {

        }
    }
}
