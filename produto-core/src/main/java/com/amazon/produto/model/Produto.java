package com.amazon.produto.model;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
