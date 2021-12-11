package com.amazon.events.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoRealizadoEvent {

    private String idPedido;
    private String usuario;
    private BigDecimal valorTotal;
    private String situacaoPagamento;
    private LocalDateTime dataPagamento;
}
