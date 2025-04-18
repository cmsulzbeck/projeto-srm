package com.srm.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MoedaDTO {
    private Long id;
    private String nome;
    private String codigo;
    private BigDecimal taxaCambio;
    private LocalDateTime dataAtualizacao;
} 