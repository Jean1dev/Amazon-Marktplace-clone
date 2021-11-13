package com.amazon.application;

import com.amazon.AppConfigTest;
import com.amazon.application.commands.CriarProdutoCommand;
import com.amazon.application.commands.SolicitarAlteracaoEstoqueReservaCommand;
import com.amazon.produto.model.Produto;
import com.amazon.produto.model.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.UUID;

import static com.amazon.config.AMQPConstants.PRODUTO_ALTERADO_QUEUE;
import static com.amazon.config.AMQPConstants.PRODUTO_QUEUE;

@DisplayName("ProdutoApplicationTest")
public class ProdutoApplicationTest extends AppConfigTest {

    @Autowired
    private ProdutoApplication produtoApplication;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProdutoRepository repository;

    @Test
    @DisplayName("deve cadastrar um produto")
    public void deveCadastrarUmProduto() {
        CriarProdutoCommand command = CriarProdutoCommand.builder()
                .imagemUrl("imagem")
                .nome("produto teste")
                .preco(BigDecimal.ONE)
                .quantidadeEstoqueAtual(1)
                .build();

        Produto produto = produtoApplication.criarProduto(command);

        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend(Mockito.eq(PRODUTO_QUEUE), Mockito.any(Produto.class));
        Assertions.assertEquals("imagem", produto.getImagemUrl());
        Assertions.assertEquals("produto teste", produto.getNome());
        Assertions.assertEquals(BigDecimal.ONE, produto.getPreco());
    }

    @Test
    @DisplayName("deve reservar um produto no estoque")
    public void deveReservarProdutoNoEstoque() {
        String id = UUID.randomUUID().toString();
        Produto produto = repository.save(Produto.builder()
                .id(id)
                .imagemUrl("imagem")
                .nome("nome")
                .preco(BigDecimal.TEN)
                .quantidadeEstoqueAtual(1)
                .quantidadeEstoqueReservado(0)
                .build());

        repository.save(produto);

        SolicitarAlteracaoEstoqueReservaCommand command = SolicitarAlteracaoEstoqueReservaCommand.builder()
                .idProduto(id)
                .quantidadeReservaEstoque(1)
                .build();

        produtoApplication.reservarNoEstoque(command);
        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend(Mockito.eq(PRODUTO_ALTERADO_QUEUE), Mockito.any(Produto.class));

        Produto produtoAtualizado = repository.findById(id).orElseThrow();
        Assertions.assertEquals(1, produtoAtualizado.getQuantidadeEstoqueReservado());
    }
}
