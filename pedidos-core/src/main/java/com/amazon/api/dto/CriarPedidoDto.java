package com.amazon.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class CriarPedidoDto {

    private String usuario;

    private List<ItemPedidoDto> item;

    @Builder
    @Getter
    @AllArgsConstructor
    public static final class ItemPedidoDto {
        private Integer quantidade;
        private String produtoReferencia;
        private String descricaoProduto;
        private BigDecimal valorUnitario;
    }
}
