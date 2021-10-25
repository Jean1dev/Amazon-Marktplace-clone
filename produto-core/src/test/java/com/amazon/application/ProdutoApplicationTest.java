package com.amazon.application;

import com.amazon.AppConfigTest;
import com.amazon.application.commands.CriarProdutoCommand;
import com.amazon.produto.model.Produto;
import com.amazon.produto.model.ProdutoRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static com.amazon.config.AMQPConstants.PRODUTO_QUEUE;

@DisplayName("ProdutoApplicationTest")
public class ProdutoApplicationTest extends AppConfigTest {

    @Autowired
    private ProdutoApplication produtoApplication;

    @Autowired
    private ProdutoRepository repository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    @DisplayName("deve cadastrar um produto")
    public void deveCadastrarUmProduto() {
        CriarProdutoCommand command = CriarProdutoCommand.builder()
                .imagemUrl("imagem")
                .nome("produto teste")
                .preco(BigDecimal.ONE)
                .build();

        Produto produto = produtoApplication.criarProduto(command);

        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend(Mockito.eq(PRODUTO_QUEUE), Mockito.any(Produto.class));
        Assertions.assertEquals("imagem", produto.getImagemUrl());
        Assertions.assertEquals("produto teste", produto.getNome());
        Assertions.assertEquals(BigDecimal.ONE, produto.getPreco());
    }
}
