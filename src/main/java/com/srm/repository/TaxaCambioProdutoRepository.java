package com.srm.repository;

import com.srm.entity.TaxaCambioProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxaCambioProdutoRepository extends JpaRepository<TaxaCambioProduto, Long>, JpaSpecificationExecutor<TaxaCambioProduto> {
    Optional<TaxaCambioProduto> findByMoedaOrigemIdAndMoedaDestinoId(Long moedaOrigemId, Long moedaDestinoId);
    Optional<TaxaCambioProduto> findByProdutoIdAndMoedaOrigemIdAndMoedaDestinoId(Long produtoId, Long moedaOrigemId, Long moedaDestinoId);
    void deleteByProdutoId(Long produtoId);
} 