package com.amazon.events.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEvent {

    private String id;
    private String usuario;
    private BigDecimal valorTotal;
    private String situacaoPedido;
}
