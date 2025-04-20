package com.srm.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TaxaCambioProdutoDTO {
    private Long id;
    private Long produtoId;
    private String produtoNome;
    private Long moedaOrigemId;
    private String moedaOrigemCodigo;
    private Long moedaDestinoId;
    private String moedaDestinoCodigo;
    private BigDecimal taxa;
    private LocalDateTime dataAtualizacao;
} 