package com.amazon.produto.model;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @Id
    private String id;
    private String nome;
    private BigDecimal preco;
    private Integer nota;
    private String imagemUrl;
    private Integer quantidadeEstoqueAtual;
    private Integer quantidadeEstoqueReservado;
}
