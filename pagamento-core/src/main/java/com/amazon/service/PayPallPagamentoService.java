package com.amazon.service;

import org.springframework.stereotype.Service;

@Service
public class PayPallPagamentoService implements PagamentoService {

    @Override
    public void efetuarCobranca() throws PagamentoException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new PagamentoException();
        }
        System.out.println("Pagamento realizado");
    }
}
