package com.amazon.application;

import com.amazon.AppConfigTest;
import com.amazon.application.commands.CriarPedidoCommand;
import com.amazon.http.ProdutoCoreRequest;
import com.amazon.http.ProdutoQueryRequest;
import com.amazon.http.dto.ProdutoDto;
import com.amazon.pedido.model.ItemPedido;
import com.amazon.pedido.model.Pedido;
import com.amazon.pedido.model.PedidoRepository;
import com.amazon.pedido.model.SituacaoPedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DisplayName("PedidoApplication")
public class PedidoApplicationTest extends AppConfigTest {

    @Autowired
    private PedidoApplication pedidoApplication;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private ProdutoQueryRequest produtoQueryRequest;

    @MockBean
    private ProdutoCoreRequest produtoCoreRequest;

    @Autowired
    private PedidoRepository repository;

    @Test
    @DisplayName("deve criar um pedido e enviar mensagem")
    public void deveCriar() {
        CriarPedidoCommand command = CriarPedidoCommand.builder()
                .usuario("meu-usuario")
                .item(IntStream.range(0, 1)
                        .mapToObj(value -> CriarPedidoCommand.ItemPedidoCommand
                                .builder()
                                .quantidade(1)
                                .descricaoProduto("desc")
                                .produtoReferencia("ref")
                                .valorUnitario(BigDecimal.ONE)
                                .build())
                        .collect(Collectors.toList()))
                .build();

        Pedido pedido = pedidoApplication.criarPedido(command);

        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pedido.class));
        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        Assertions.assertNotNull(pedido.getId());
        Assertions.assertNotNull(pedido.getDataPedido());
        Assertions.assertEquals("meu-usuario", pedido.getUsuario());
        Assertions.assertNull(pedido.getPagamentoReferencia());
        Assertions.assertEquals(SituacaoPedido.PEDIDO_REALIZADO, pedido.getSituacaoPedido());
        Assertions.assertEquals(0, BigDecimal.ONE.compareTo(pedido.getValorTotal()));
    }

    @Test
    @DisplayName("Deve processar um pedido")
    public void deveProcessarUmPedido() {
        String id = UUID.randomUUID().toString();
        Pedido pedido = Pedido.builder()
                .id(id)
                .dataPedido(LocalDateTime.now())
                .situacaoPedido(SituacaoPedido.PEDIDO_REALIZADO)
                .usuario("usuario")
                .itens(IntStream
                        .range(0, 1)
                        .mapToObj(value -> ItemPedido.builder()
                                .id(UUID.randomUUID().toString())
                                .descricaoProduto("descricao")
                                .produtoReferencia("produto ref")
                                .quantidade(1)
                                .valorUnitario(BigDecimal.TEN)
                                .build())
                        .collect(Collectors.toList())
                )
                .build();

        repository.save(pedido);

        ProdutoDto produtoDto = new ProdutoDto(5, 0);
        Mockito.when(produtoQueryRequest.buscarProduto(ArgumentMatchers.anyString())).thenReturn(produtoDto);

        pedidoApplication.processarPedido(id);

        Mockito.verify(produtoCoreRequest, Mockito.times(1)).solicitarReservaDeEstoque(ArgumentMatchers.anyString(), ArgumentMatchers.eq(1));
    }

    @Test
    @DisplayName("Deve processar um pedido e falhar pq nao tem a quantidade suficiente do produto em estoque")
    public void deveProcessarUmPedidoEFlharPorCausaDoEstoque() {
        String id = UUID.randomUUID().toString();
        Pedido pedido = Pedido.builder()
                .id(id)
                .dataPedido(LocalDateTime.now())
                .situacaoPedido(SituacaoPedido.PEDIDO_REALIZADO)
                .usuario("usuario")
                .itens(IntStream
                        .range(0, 1)
                        .mapToObj(value -> ItemPedido.builder()
                                .id(UUID.randomUUID().toString())
                                .descricaoProduto("descricao")
                                .produtoReferencia("produto ref")
                                .quantidade(1)
                                .valorUnitario(BigDecimal.TEN)
                                .build())
                        .collect(Collectors.toList())
                )
                .build();

        repository.save(pedido);

        ProdutoDto produtoDto = new ProdutoDto(0, 0);
        Mockito.when(produtoQueryRequest.buscarProduto(ArgumentMatchers.anyString())).thenReturn(produtoDto);

        Assertions.assertThrows(ValidationException.class, () -> pedidoApplication.processarPedido(id), "Pedido não pode ser feito porque falta itens no estoque");
    }

    @Test
    @DisplayName("Deve processar um pedido e falhar pq produto já esta reservado")
    public void deveProcessarUmPedidoEFlharPorQueProdutoJaEstaReservado() {
        String id = UUID.randomUUID().toString();
        Pedido pedido = Pedido.builder()
                .id(id)
                .dataPedido(LocalDateTime.now())
                .situacaoPedido(SituacaoPedido.PEDIDO_REALIZADO)
                .usuario("usuario")
                .itens(IntStream
                        .range(0, 1)
                        .mapToObj(value -> ItemPedido.builder()
                                .id(UUID.randomUUID().toString())
                                .descricaoProduto("descricao")
                                .produtoReferencia("produto ref")
                                .quantidade(1)
                                .valorUnitario(BigDecimal.TEN)
                                .build())
                        .collect(Collectors.toList())
                )
                .build();

        repository.save(pedido);

        ProdutoDto produtoDto = new ProdutoDto(1, 1);
        Mockito.when(produtoQueryRequest.buscarProduto(ArgumentMatchers.anyString())).thenReturn(produtoDto);

        Assertions.assertThrows(ValidationException.class, () -> pedidoApplication.processarPedido(id), "Pedido não pode ser feito porque falta itens no estoque");
    }
}
