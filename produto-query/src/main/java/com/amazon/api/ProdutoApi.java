package com.amazon.api;

import com.amazon.produto.model.Produto;
import com.amazon.produto.model.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = ProdutoApi.PATH)
@RequiredArgsConstructor
public class ProdutoApi {

    public static final String PATH = "produtos";
    private final ProdutoRepository repository;

    @GetMapping
    public List<Produto> findAll() {
        return repository.findAll();
    }

}
