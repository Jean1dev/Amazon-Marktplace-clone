package com.amazon.application.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class CriarPedidoCommand {

    private String usuario;

    private List<ItemPedidoCommand> item;

    @Builder
    @Data
    @AllArgsConstructor
    public static final class ItemPedidoCommand {
        private Integer quantidade;
        private String produtoReferencia;
        private String descricaoProduto;
        private BigDecimal valorUnitario;
    }
}
