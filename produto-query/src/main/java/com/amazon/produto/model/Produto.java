package com.amazon.produto.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
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
