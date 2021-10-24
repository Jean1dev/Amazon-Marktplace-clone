package com.amazon.application;

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
                .build());
    }
}
