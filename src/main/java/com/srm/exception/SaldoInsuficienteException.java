package com.srm.exception;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(Long produtoId, Integer quantidade) {
        super(String.format("Saldo insuficiente para o produto %d. Quantidade solicitada: %d", produtoId, quantidade));
    }
} 