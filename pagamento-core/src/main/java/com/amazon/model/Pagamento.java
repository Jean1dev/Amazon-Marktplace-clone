package com.amazon.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
