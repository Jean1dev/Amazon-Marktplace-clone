package com.amazon.model;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    private String id;

    @NotNull
    private LocalDateTime dataHoraPagamento;

    @NotNull
    private String usuario;

    @NotNull
    private String pedidoRef;

    @Positive
    private BigDecimal valorTotal;
}
