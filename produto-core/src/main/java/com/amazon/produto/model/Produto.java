package com.amazon.produto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @Id
    private String id;

    @NotEmpty
    private String nome;

    @Positive
    private BigDecimal preco;

    private Integer nota;

    @NotEmpty
    private String imagemUrl;
}
