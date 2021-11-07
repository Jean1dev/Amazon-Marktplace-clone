package com.amazon.application;

import com.amazon.AppConfigTest;
import com.amazon.events.event.PedidoEvent;
import com.amazon.pedido.model.Pedido;
import com.amazon.pedido.model.PedidoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DisplayName("PedidoApplicationTest")
public class PedidoApplicationTest extends AppConfigTest {

    @Autowired
    private PedidoApplication application;

    @Autowired
    private PedidoRepository repository;

    @Test
    @DisplayName("deve criar um pedido")
    public void deveCriar() {
        PedidoEvent pedidoEvent = PedidoEvent.builder()
                .id(UUID.randomUUID().toString())
                .dataPedido(LocalDateTime.now())
                .situacaoPedido("pedido feito")
                .pagamentoReferencia(null)
                .usuario("usuario")
                .valorTotal(BigDecimal.TEN)
                .itens(List.of(PedidoEvent.ItemPedidoEvent.builder().id(UUID.randomUUID().toString()).build()))
                .build();

        application.onCreate(pedidoEvent);
        Pedido pedido = repository.findById(pedidoEvent.getId()).orElseThrow();
        Assertions.assertEquals(pedido.getSituacaoPedido(), pedidoEvent.getSituacaoPedido());
        Assertions.assertEquals(pedido.getUsuario(), pedidoEvent.getUsuario());
        Assertions.assertEquals(pedido.getDataPedido().getMonth(), pedidoEvent.getDataPedido().getMonth());
        Assertions.assertNull(pedido.getPagamentoReferencia());
        Assertions.assertEquals(1, pedido.getItens().size());
    }
}
