package com.amazon.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class ProdutoCoreRequest {

    private final RestTemplate restTemplate;

    @Value("${apis.produto_core}")
    private String BASE_URL;

    public ProdutoCoreRequest() {
        restTemplate = new RestTemplate();
    }

    public void solicitarReservaDeEstoque(String id, Integer quantidadeReservaEstoque) {
        String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .pathSegment("reservar-estoque")
                .build()
                .toUriString();

        Map<String, Object> payload = Map.of("idProduto", id, "quantidadeReservaEstoque", quantidadeReservaEstoque);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(payload, null);
        restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Object.class).getBody();
    }

    public void solicitarBaixaDeEstoque(String id, Integer quantidade) {
        String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .pathSegment("baixar-estoque")
                .build()
                .toUriString();

        Map<String, Object> payload = Map.of("idProduto", id, "quantidade", quantidade);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(payload, null);
        restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Object.class).getBody();
    }
}
