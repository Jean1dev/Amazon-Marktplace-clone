package com.amazon.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
public class SolicitarAlteracaoEstoqueReservaDto {

    @NotNull
    private String idProduto;

    private Integer quantidadeReservaEstoque;
}
