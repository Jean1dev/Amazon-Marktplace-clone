package com.amazon.events.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEvent {

    private String id;
    private LocalDateTime dataPedido;
    private String usuario;
    private String situacaoPedido;
    private String pagamentoReferencia;
    private BigDecimal valorTotal;
    private List<ItemPedidoEvent> itens;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemPedidoEvent {
        private String id;
        private Integer quantidade;
        private String produtoReferencia;
        private String descricaoProduto;
        private BigDecimal valorUnitario;
    }
}
