package com.srm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.srm.enums.TipoTransacao;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO {
    private Long id;
    
    @NotNull(message = "O tipo da transação é obrigatório")
    private TipoTransacao tipoTransacao;
    
    private BigDecimal valor;
    
    private LocalDateTime dataTransacao;
    
    private String descricao;
    
    @NotNull(message = "O ID do produto é obrigatório")
    private Long produtoId;
    
    @NotNull(message = "O código da moeda de origem é obrigatório")
    private String moedaOrigemCodigo;
    
    @NotNull(message = "O código da moeda de destino é obrigatório")
    private String moedaDestinoCodigo;
    
    @NotNull(message = "O ID do reino é obrigatório")
    private Long reinoId;
    
    private String produtoNome;
    
    private String reinoNome;
    
    private Long moedaOrigemId;
    
    private Long moedaDestinoId;
} 