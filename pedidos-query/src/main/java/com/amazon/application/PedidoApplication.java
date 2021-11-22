package com.amazon.application;

import com.amazon.events.event.PedidoEvent;
import com.amazon.pedido.model.ItemPedido;
import com.amazon.pedido.model.Pedido;
import com.amazon.pedido.model.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PedidoApplication {

    private final PedidoRepository repository;

    public void onCreate(PedidoEvent pedidoEvent) {
        Pedido pedidoRef = Pedido.builder().id(pedidoEvent.getId()).build();
        repository.save(Pedido.builder()
                .id(pedidoEvent.getId())
                .dataPedido(pedidoEvent.getDataPedido())
                .situacaoPedido(pedidoEvent.getSituacaoPedido())
                .pagamentoReferencia(pedidoEvent.getPagamentoReferencia())
                .usuario(pedidoEvent.getUsuario())
                .valorTotal(pedidoEvent.getValorTotal())
                .itens(pedidoEvent.getItens().stream()
                        .map(itemPedidoEvent -> ItemPedido.builder()
                                .id(itemPedidoEvent.getId())
                                .valorUnitario(itemPedidoEvent.getValorUnitario())
                                .quantidade(itemPedidoEvent.getQuantidade())
                                .produtoReferencia(itemPedidoEvent.getProdutoReferencia())
                                .descricaoProduto(itemPedidoEvent.getDescricaoProduto())
                                .pedido(pedidoRef)
                                .build())
                        .collect(Collectors.toList()))
                .build());
    }
}
