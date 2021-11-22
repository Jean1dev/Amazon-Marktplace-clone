package com.amazon.application;

import com.amazon.AppConfigTest;
import com.amazon.application.commands.CriarPedidoCommand;
import com.amazon.pedido.model.Pedido;
import com.amazon.pedido.model.SituacaoPedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DisplayName("PedidoApplication")
public class PedidoApplicationTest extends AppConfigTest {

    @Autowired
    private PedidoApplication pedidoApplication;

    @MockBean
    private RabbitTemplate rabbitTemplate;

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

        Mockito.verify(rabbitTemplate, Mockito.times(2)).convertAndSend(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pedido.class));
        Assertions.assertNotNull(pedido.getId());
        Assertions.assertNotNull(pedido.getDataPedido());
        Assertions.assertEquals("meu-usuario", pedido.getUsuario());
        Assertions.assertNull(pedido.getPagamentoReferencia());
        Assertions.assertEquals(SituacaoPedido.PEDIDO_REALIZADO, pedido.getSituacaoPedido());
        Assertions.assertEquals(0, BigDecimal.ONE.compareTo(pedido.getValorTotal()));
    }
}
