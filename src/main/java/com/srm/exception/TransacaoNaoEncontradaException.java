package com.srm.exception;

public class TransacaoNaoEncontradaException extends RuntimeException {
    public TransacaoNaoEncontradaException(Long id) {
        super("Transação não encontrada com ID: " + id);
    }
} 