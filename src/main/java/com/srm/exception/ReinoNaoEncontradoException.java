package com.srm.exception;

public class ReinoNaoEncontradoException extends RuntimeException {
    
    public ReinoNaoEncontradoException(Long id) {
        super("Reino não encontrado com ID: " + id);
    }
} 