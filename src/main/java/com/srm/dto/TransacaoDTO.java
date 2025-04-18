package com.srm.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransacaoDTO {
    private Long id;
    private Long produtoId;
    private Integer quantidade;
    private Double valorTotal;
    private LocalDateTime dataTransacao;
    private String tipo;
} 