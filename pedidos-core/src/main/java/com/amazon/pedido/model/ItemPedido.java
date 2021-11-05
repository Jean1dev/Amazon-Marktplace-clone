package com.amazon.pedido.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {

    @Id
    private String id;

    @Positive
    private Integer quantidade;

    @NotNull
    private String produtoReferencia;

    @NotEmpty
    private String descricaoProduto;

    @Positive
    private BigDecimal valorUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pedido pedido;
}
