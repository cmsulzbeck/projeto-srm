package com.srm.exception;

public class MoedaNaoEncontradaException extends RuntimeException {
    public MoedaNaoEncontradaException(String codigo) {
        super("Moeda não encontrada: " + codigo);
    }
} 