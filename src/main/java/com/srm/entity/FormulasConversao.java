package com.srm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FORMULAS_CONVERSAO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormulasConversao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "reino_id", nullable = false)
    private Reino reino;

    @Column(nullable = false)
    private String formula;
} 