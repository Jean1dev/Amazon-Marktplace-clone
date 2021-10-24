package com.amazon.application;

import com.amazon.application.commands.CriarProdutoCommand;
import com.amazon.produto.model.Produto;
import com.amazon.produto.model.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
                .build());

        rabbitTemplate.convertAndSend(PRODUTO_QUEUE, produto);
        return produto;
    }
}
