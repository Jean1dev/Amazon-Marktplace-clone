package com.amazon.application;

import com.amazon.events.event.ProdutoAlteradoEvent;
import com.amazon.events.event.ProdutoCriadoEvent;
import com.amazon.produto.model.Produto;
import com.amazon.produto.model.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProdutoApplication {

    private final ProdutoRepository repository;

    public void onCreate(ProdutoCriadoEvent event) {
        repository.save(Produto.builder()
                .id(event.getId())
                .preco(event.getPreco())
                .imagemUrl(event.getImagemUrl())
                .nome(event.getNome())
                .quantidadeEstoqueReservado(event.getQuantidadeEstoqueReservado())
                .quantidadeEstoqueAtual(event.getQuantidadeEstoqueAtual())
                .build());
    }

    public void onChange(ProdutoAlteradoEvent event) {
        Produto produto = repository.findById(event.getId()).orElseThrow();
        produto.setPreco(event.getPreco());
        produto.setImagemUrl(event.getImagemUrl());
        produto.setNome(event.getNome());
        produto.setQuantidadeEstoqueReservado(event.getQuantidadeEstoqueReservado());
        produto.setQuantidadeEstoqueAtual(event.getQuantidadeEstoqueAtual());
        repository.save(produto);
    }
}
