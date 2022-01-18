package com.amazon.application.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@Data
@AllArgsConstructor
public class SolicitarAlteracaoEstoqueReservaCommand {

    private String idProduto;

    private Integer quantidadeReservaEstoque;

    private Integer quantidade;
}
