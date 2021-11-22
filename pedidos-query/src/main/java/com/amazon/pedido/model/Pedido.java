package com.amazon.pedido.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    private String id;

    private LocalDateTime dataPedido;

    private String usuario;

    private String situacaoPedido;

    private String pagamentoReferencia;

    private BigDecimal valorTotal;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pedido")
    private List<ItemPedido> itens;
}
