package com.amazon.produto.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

    @NotEmpty
    private String nome;

    @Positive
    private BigDecimal preco;

    private Integer nota;

    @NotEmpty
    private String imagemUrl;

    @NotNull
    private Integer quantidadeEstoqueAtual;

    private Integer quantidadeEstoqueReservado;
}
