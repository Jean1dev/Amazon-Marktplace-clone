package com.amazon.api;

import com.amazon.api.dto.CriarProdutoDto;
import com.amazon.application.ProdutoApplication;
import com.amazon.application.commands.CriarProdutoCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = ProdutoApi.PATH)
@RequiredArgsConstructor
public class ProdutoApi {

    public static final String PATH = "produto";

    private final ProdutoApplication application;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(@RequestBody CriarProdutoDto dto) {
        application.criarProduto(CriarProdutoCommand.builder()
                .preco(dto.getPreco())
                .nome(dto.getNome())
                .imagemUrl(dto.getImagemUrl())
                .build());
    }
}
