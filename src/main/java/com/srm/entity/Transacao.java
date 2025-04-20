package com.srm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.srm.enums.TipoTransacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRANSACOES")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "reino_id", nullable = false)
    private Reino reino;

    @ManyToOne
    @JoinColumn(name = "moeda_origem_id", nullable = false)
    private Moeda moedaOrigem;

    @ManyToOne
    @JoinColumn(name = "moeda_destino_id", nullable = false)
    private Moeda moedaDestino;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "data", nullable = false)
    private LocalDateTime dataTransacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoTransacao tipoTransacao;

    @Column(length = 1000)
    private String descricao;

    @PrePersist
    public void prePersist() {
        this.dataTransacao = LocalDateTime.now();
    }
} 