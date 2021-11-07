package com.amazon.pedido.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

    private Integer quantidade;

    private String produtoReferencia;

    private String descricaoProduto;

    private BigDecimal valorUnitario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Pedido pedido;
}
