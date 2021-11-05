package com.amazon.api;

import com.amazon.api.dto.CriarPedidoDto;
import com.amazon.application.PedidoApplication;
import com.amazon.application.commands.CriarPedidoCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(path = PedidoApi.PATH)
@RequiredArgsConstructor
public class PedidoApi {

    public static final String PATH = "pedido";

    private final PedidoApplication pedidoApplication;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarPedido(@RequestBody CriarPedidoDto dto) {
        pedidoApplication.criarPedido(CriarPedidoCommand.builder()
                .usuario(dto.getUsuario())
                .item(dto.getItem()
                        .stream()
                        .map(itemPedidoDto -> CriarPedidoCommand.ItemPedidoCommand.builder()
                                .descricaoProduto(itemPedidoDto.getDescricaoProduto())
                                .produtoReferencia(itemPedidoDto.getProdutoReferencia())
                                .quantidade(itemPedidoDto.getQuantidade())
                                .valorUnitario(itemPedidoDto.getValorUnitario())
                                .build())
                        .collect(Collectors.toList()))
                .build());
    }
}
