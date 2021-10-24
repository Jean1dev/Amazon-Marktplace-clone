package com.amazon.produto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @Id
    private String id;
    private String nome;
    private BigDecimal preco;
    private Integer nota;
    private String imagemUrl;
}
