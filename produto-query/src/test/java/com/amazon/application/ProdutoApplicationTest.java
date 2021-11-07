package com.amazon.application;

import com.amazon.AppConfigTest;
import com.amazon.events.event.ProdutoCriadoEvent;
import com.amazon.produto.model.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

@DisplayName("ProdutoApplicationTest")
public class ProdutoApplicationTest extends AppConfigTest {

    @Autowired
    private ProdutoApplication application;

    @Autowired
    private ProdutoRepository repository;

    @Test
    @DisplayName("deve salvar um produto")
    public void deveSalvar() {
        String id = UUID.randomUUID().toString();
        ProdutoCriadoEvent event = new ProdutoCriadoEvent(id, "nome", BigDecimal.ONE, null, "imagem", BigDecimal.ONE, BigDecimal.ONE);
        application.onCreate(event);
        repository.findById(id).ifPresent(produto -> {
            Assertions.assertEquals(id, produto.getId());
            Assertions.assertEquals("nome", produto.getNome());
            Assertions.assertEquals(0, produto.getPreco().compareTo(BigDecimal.ONE));
            Assertions.assertEquals("imagem", produto.getImagemUrl());
            Assertions.assertNull(produto.getNota());
        });
    }
}
