package com.srm.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ConversaoDTO {
    private String moedaOrigem;
    private String moedaDestino;
    private String produto;
    private BigDecimal valorOriginal;
    private BigDecimal valorConvertido;
    private BigDecimal taxaCambio;
} 