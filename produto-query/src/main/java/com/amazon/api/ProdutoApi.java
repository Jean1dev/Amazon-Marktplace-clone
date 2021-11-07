package com.amazon.api;

import com.amazon.produto.model.Produto;
import com.amazon.produto.model.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "{id}")
    public Produto findById(@PathVariable("id") String id) {
        return repository.findById(id).orElseThrow();
    }
}
