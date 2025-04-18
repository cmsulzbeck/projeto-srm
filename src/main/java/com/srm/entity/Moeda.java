package com.srm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "MOEDA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Moeda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(name = "taxa_cambio", nullable = false)
    private BigDecimal taxaCambio;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    @PreUpdate
    public void prePersistUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
} 