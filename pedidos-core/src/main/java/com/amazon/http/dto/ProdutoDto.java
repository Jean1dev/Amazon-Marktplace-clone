package com.amazon.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProdutoDto {
    private Integer quantidadeEstoqueAtual;
    private Integer quantidadeEstoqueReservado;
}
