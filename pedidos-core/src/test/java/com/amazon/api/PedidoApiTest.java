package com.amazon.api;

import com.amazon.AppConfigTest;
import com.amazon.api.dto.CriarPedidoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("PedidoApi")
public class PedidoApiTest extends AppConfigTest {

    public static final String PATH = "/" + PedidoApi.PATH;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("deve Criar um pedido via api")
    public void deveCriar() throws Exception {
        CriarPedidoDto dto = CriarPedidoDto.builder()
                .usuario("meu-usuario")
                .item(IntStream.range(0, 1)
                        .mapToObj(value -> CriarPedidoDto.ItemPedidoDto
                                .builder()
                                .quantidade(1)
                                .descricaoProduto("desc")
                                .produtoReferencia("ref")
                                .valorUnitario(BigDecimal.ONE)
                                .build())
                        .collect(Collectors.toList()))
                .build();

        String dtoAsJson = new ObjectMapper().writeValueAsString(dto);
        mvc.perform(request(HttpMethod.POST, PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJson))
                .andExpect(status().isCreated());
    }
}
