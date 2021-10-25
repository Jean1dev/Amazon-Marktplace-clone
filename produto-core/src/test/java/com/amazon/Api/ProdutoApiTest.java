package com.amazon.Api;

import com.amazon.AppConfigTest;
import com.amazon.api.ProdutoApi;
import com.amazon.api.dto.CriarProdutoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ProdutoApi ")
public class ProdutoApiTest extends AppConfigTest {

    public static final String PATH = "/" + ProdutoApi.PATH;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("deve criar um produto via api")
    public void deveCriarUmProdutoViaApi() throws Exception {
        CriarProdutoDto dto = CriarProdutoDto.builder()
                .imagemUrl("imagem")
                .nome("nome")
                .preco(BigDecimal.ONE)
                .build();

        String dtoAsJson = new ObjectMapper().writeValueAsString(dto);
        mvc.perform(request(HttpMethod.POST, PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJson))
                .andExpect(status().isCreated());
    }
}
