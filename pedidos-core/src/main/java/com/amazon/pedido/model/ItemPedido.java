package com.amazon.pedido.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Entity
@Getter
@Setter
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Pedido pedido;
}
