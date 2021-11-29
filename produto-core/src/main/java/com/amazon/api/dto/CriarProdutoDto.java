package com.amazon.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
public final class CriarProdutoDto {

    @NotEmpty
    private final String nome;

    @Positive
    private final BigDecimal preco;

    @NotEmpty
    private final String imagemUrl;

    @NotNull
    private Integer quantidadeEstoqueAtual;

    private Integer quantidadeEstoqueReservado;
}
