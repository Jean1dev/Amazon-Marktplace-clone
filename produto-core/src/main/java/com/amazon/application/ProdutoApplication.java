package com.amazon.application;

import com.amazon.application.commands.CriarProdutoCommand;
import com.amazon.application.commands.SolicitarAlteracaoEstoqueReservaCommand;
import com.amazon.produto.model.Produto;
import com.amazon.produto.model.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static com.amazon.config.AMQPConstants.PRODUTO_ALTERADO_QUEUE;
import static com.amazon.config.AMQPConstants.PRODUTO_QUEUE;

@Service
@Transactional
@RequiredArgsConstructor
public class ProdutoApplication {

    private final ProdutoRepository repository;

    private final RabbitTemplate rabbitTemplate;

    public Produto criarProduto(CriarProdutoCommand command) {
        Produto produto = repository.save(Produto.builder()
                .id(UUID.randomUUID().toString())
                .imagemUrl(command.getImagemUrl())
                .nome(command.getNome())
                .preco(command.getPreco())
                .quantidadeEstoqueAtual(command.getQuantidadeEstoqueAtual())
                .quantidadeEstoqueReservado(command.getQuantidadeEstoqueReservado())
                .build());

        rabbitTemplate.convertAndSend(PRODUTO_QUEUE, produto);
        return produto;
    }

    public void reservarNoEstoque(SolicitarAlteracaoEstoqueReservaCommand command) {
        Produto produto = repository.findById(command.getIdProduto()).orElseThrow();

        if (Objects.isNull(produto.getQuantidadeEstoqueReservado())) {
            produto.setQuantidadeEstoqueReservado(0);
        }

        produto.setQuantidadeEstoqueReservado(produto.getQuantidadeEstoqueReservado() + command.getQuantidadeReservaEstoque());
        repository.save(produto);
        rabbitTemplate.convertAndSend(PRODUTO_ALTERADO_QUEUE, produto);
    }

    public void baixarEstoque(SolicitarAlteracaoEstoqueReservaCommand command) {
        Produto produto = repository.findById(command.getIdProduto()).orElseThrow();
        produto.setQuantidadeEstoqueAtual(produto.getQuantidadeEstoqueAtual() - command.getQuantidade());
        produto.setQuantidadeEstoqueReservado(produto.getQuantidadeEstoqueReservado() - command.getQuantidade());
        repository.save(produto);
        rabbitTemplate.convertAndSend(PRODUTO_ALTERADO_QUEUE, produto);
    }
}
