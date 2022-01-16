package com.amazon.api;

import com.amazon.pedido.model.Pedido;
import com.amazon.pedido.model.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = PedidoApi.PATH)
@RequiredArgsConstructor
public class PedidoApi {

    public static final String PATH = "pedidos";

    private final PedidoRepository repository;

    @GetMapping
    public List<Pedido> findAll() {
        return repository.findAll();
    }
}
