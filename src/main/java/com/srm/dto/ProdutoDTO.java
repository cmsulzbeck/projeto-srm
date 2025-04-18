package com.srm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    @NotBlank(message = "A descrição do produto é obrigatória")
    private String descricao;

    @NotNull(message = "O preço do produto é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "A moeda do produto é obrigatória")
    private String moedaCodigo;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Positive(message = "A quantidade em estoque deve ser maior que zero")
    private Integer quantidadeEstoque;
} 