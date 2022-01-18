package com.amazon.api;

import com.amazon.api.dto.CriarProdutoDto;
import com.amazon.api.dto.SolicitarAlteracaoEstoqueReservaDto;
import com.amazon.application.ProdutoApplication;
import com.amazon.application.commands.CriarProdutoCommand;
import com.amazon.application.commands.SolicitarAlteracaoEstoqueReservaCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = ProdutoApi.PATH)
@RequiredArgsConstructor
public class ProdutoApi {

    public static final String PATH = "produto";

    private final ProdutoApplication application;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(@RequestBody @Valid CriarProdutoDto dto) {
        application.criarProduto(CriarProdutoCommand.builder()
                .preco(dto.getPreco())
                .nome(dto.getNome())
                .imagemUrl(dto.getImagemUrl())
                .quantidadeEstoqueAtual(dto.getQuantidadeEstoqueAtual())
                .quantidadeEstoqueReservado(dto.getQuantidadeEstoqueReservado())
                .build());
    }

    @PostMapping(path = "reservar-estoque")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void reservarEstoque(@RequestBody SolicitarAlteracaoEstoqueReservaDto dto) {
        application.reservarNoEstoque(SolicitarAlteracaoEstoqueReservaCommand.builder()
                .idProduto(dto.getIdProduto())
                .quantidadeReservaEstoque(dto.getQuantidadeReservaEstoque())
                .build());
    }

    @PostMapping(path = "baixar-estoque")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void baixarEstoque(@RequestBody SolicitarAlteracaoEstoqueReservaDto dto) {
        application.baixarEstoque(SolicitarAlteracaoEstoqueReservaCommand.builder()
                .idProduto(dto.getIdProduto())
                .quantidade(dto.getQuantidade())
                .build());
    }
}
