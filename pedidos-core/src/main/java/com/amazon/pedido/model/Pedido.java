package com.amazon.pedido.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor
public class Pedido {

    @Id
    private String id;

    @NotNull
    private LocalDateTime dataPedido;

    @NotEmpty
    private String usuario;

    @NotNull
    private SituacaoPedido situacaoPedido;

    private String pagamentoReferencia;

    @Positive
    private BigDecimal valorTotal;

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pedido")
    private List<ItemPedido> itens;

    @Builder
    public Pedido(String id,
                  LocalDateTime dataPedido,
                  String usuario,
                  SituacaoPedido situacaoPedido,
                  String pagamentoReferencia,
                  BigDecimal valorTotal,
                  List<ItemPedido> itens) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.usuario = usuario;
        this.situacaoPedido = situacaoPedido;
        this.pagamentoReferencia = pagamentoReferencia;
        this.itens = itens;
        this.valorTotal = this.calcularTotal();
    }

    private BigDecimal calcularTotal() {
        return itens
                .stream()
                .map(itemPedido -> itemPedido.getValorUnitario().multiply(BigDecimal.valueOf(itemPedido.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
