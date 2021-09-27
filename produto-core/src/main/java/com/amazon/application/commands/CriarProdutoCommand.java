package com.amazon.application.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Getter
@Data
@AllArgsConstructor
public final class CriarProdutoCommand {

    @NotEmpty
    private final String nome;

    @Positive
    private final BigDecimal preco;

    @NotEmpty
    private final String imagemUrl;
}
