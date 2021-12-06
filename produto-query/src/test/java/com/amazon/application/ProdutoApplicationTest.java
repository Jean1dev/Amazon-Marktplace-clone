package com.amazon.application;

import com.amazon.AppConfigTest;
import com.amazon.events.event.ProdutoAlteradoEvent;
import com.amazon.events.event.ProdutoCriadoEvent;
import com.amazon.produto.model.Produto;
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
        ProdutoCriadoEvent event = new ProdutoCriadoEvent(id, "nome", BigDecimal.ONE, null, "imagem", 1, 1);
        application.onCreate(event);
        repository.findById(id).ifPresent(produto -> {
            Assertions.assertEquals(id, produto.getId());
            Assertions.assertEquals("nome", produto.getNome());
            Assertions.assertEquals(0, produto.getPreco().compareTo(BigDecimal.ONE));
            Assertions.assertEquals("imagem", produto.getImagemUrl());
            Assertions.assertNull(produto.getNota());
        });
    }

    @Test
    @DisplayName("deve alterar o produto")
    public void deveAlterarProduto() {
        String id = UUID.randomUUID().toString();
        Produto produto = repository.save(Produto.builder()
                .id(id)
                .preco(BigDecimal.ONE)
                .imagemUrl("event.getImagemUrl()")
                .nome("event.getNome()")
                .quantidadeEstoqueReservado(1)
                .quantidadeEstoqueAtual(2)
                .build());

        ProdutoAlteradoEvent event = ProdutoAlteradoEvent.builder()
                .id(id)
                .nome("nome alterado")
                .quantidadeEstoqueAtual(2)
                .quantidadeEstoqueReservado(3)
                .nota(5)
                .imagemUrl("")
                .preco(BigDecimal.ZERO)
                .build();

        application.onChange(event);

        Produto produtoAtualizado = repository.findById(id).orElseThrow();
        Assertions.assertEquals("nome alterado", produtoAtualizado.getNome());
        Assertions.assertEquals(2, produtoAtualizado.getQuantidadeEstoqueAtual());
        Assertions.assertEquals(3, produtoAtualizado.getQuantidadeEstoqueReservado());
        Assertions.assertEquals("", produtoAtualizado.getImagemUrl());
        Assertions.assertEquals(0, BigDecimal.ZERO.compareTo(produtoAtualizado.getPreco()));
    }
}
