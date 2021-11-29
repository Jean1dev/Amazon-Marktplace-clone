package com.amazon.http;

import com.amazon.http.dto.ProdutoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ProdutoQueryRequest {

    private final RestTemplate restTemplate;

    @Value("${apis.produto_query}")
    private String BASE_URL;

    public ProdutoQueryRequest() {
        restTemplate = new RestTemplate();
    }

    public ProdutoDto buscarProduto(String id) {
        String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .pathSegment(id)
                .build()
                .toUriString();

        HttpEntity<Object> httpEntity = new HttpEntity<>(null);
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, ProdutoDto.class).getBody();
    }
}
