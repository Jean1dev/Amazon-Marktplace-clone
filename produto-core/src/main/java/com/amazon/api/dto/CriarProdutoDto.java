package com.amazon.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull
    private Integer quantidadeEstoqueReservado;
}
