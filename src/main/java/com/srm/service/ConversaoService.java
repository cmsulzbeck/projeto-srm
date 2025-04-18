package com.srm.service;

import com.srm.entity.Moeda;
import com.srm.repository.MoedaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ConversaoService {

    @Autowired
    private MoedaRepository moedaRepository;

    public BigDecimal converter(String moedaOrigem, String moedaDestino, BigDecimal valor) {
        Moeda origem = moedaRepository.findByCodigo(moedaOrigem);
        if (origem == null) {
            throw new IllegalArgumentException("Moeda de origem não encontrada: " + moedaOrigem);
        }
        
        Moeda destino = moedaRepository.findByCodigo(moedaDestino);
        if (destino == null) {
            throw new IllegalArgumentException("Moeda de destino não encontrada: " + moedaDestino);
        }

        // Primeiro converte para dólar
        BigDecimal valorEmDolar = valor.divide(origem.getTaxaCambio(), 6, RoundingMode.HALF_UP);
        
        // Depois converte para a moeda de destino
        return valorEmDolar.multiply(destino.getTaxaCambio()).setScale(2, RoundingMode.HALF_UP);
    }

    public List<Moeda> listarMoedas() {
        return moedaRepository.findAll();
    }
} 