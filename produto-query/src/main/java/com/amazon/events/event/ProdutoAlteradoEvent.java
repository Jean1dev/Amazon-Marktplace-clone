package com.amazon.events.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoAlteradoEvent {

    private String id;
    private String nome;
    private BigDecimal preco;
    private Integer nota;
    private String imagemUrl;
    private Integer quantidadeEstoqueAtual;
    private Integer quantidadeEstoqueReservado;
}
