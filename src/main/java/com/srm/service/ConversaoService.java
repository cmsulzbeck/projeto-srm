package com.srm.service;

import com.srm.dto.TransacaoDTO;
import com.srm.entity.*;
import com.srm.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversaoService {

    private final TaxaCambioProdutoRepository taxaCambioProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final MoedaRepository moedaRepository;
    private final ReinoRepository reinoRepository;
    private final TransacaoRepository transacaoRepository;

    @Transactional
    public BigDecimal converterMoedas(Long moedaOrigemId, Long moedaDestinoId, BigDecimal valor) {
        Moeda moedaOrigem = moedaRepository.findById(moedaOrigemId)
                .orElseThrow(() -> new RuntimeException("Moeda origem não encontrada"));
        Moeda moedaDestino = moedaRepository.findById(moedaDestinoId)
                .orElseThrow(() -> new RuntimeException("Moeda destino não encontrada"));
        
        // Convertendo para a moeda base (OR - Ouro Real) e depois para a moeda destino
        BigDecimal valorEmOuro = valor.divide(moedaOrigem.getTaxaCambio(), 10, RoundingMode.HALF_UP);
        return valorEmOuro.multiply(moedaDestino.getTaxaCambio());
    }

    @Transactional
    public BigDecimal converterProduto(Long produtoId, Long moedaOrigemId, Long moedaDestinoId, BigDecimal valor) {
        TaxaCambioProduto taxa = taxaCambioProdutoRepository
                .findByProdutoIdAndMoedaOrigemIdAndMoedaDestinoId(produtoId, moedaOrigemId, moedaDestinoId)
                .orElseThrow(() -> new RuntimeException("Taxa de câmbio não encontrada para o produto"));
        
        return valor.multiply(taxa.getTaxa());
    }

    @Cacheable(value = "taxasCambio", key = "#produtoId + '-' + #moedaOrigemId + '-' + #moedaDestinoId")
    public BigDecimal getTaxaCambioProduto(Long produtoId, Long moedaOrigemId, Long moedaDestinoId) {
        return taxaCambioProdutoRepository
            .findByProdutoIdAndMoedaOrigemIdAndMoedaDestinoId(produtoId, moedaOrigemId, moedaDestinoId)
            .map(TaxaCambioProduto::getTaxa)
            .orElseThrow(() -> new RuntimeException("Taxa de câmbio não encontrada"));
    }

    @Transactional
    @CacheEvict(value = "taxasCambio", key = "#produtoId + '-' + #moedaOrigemId + '-' + #moedaDestinoId")
    public TaxaCambioProduto atualizarTaxa(Long produtoId, Long moedaOrigemId, Long moedaDestinoId, BigDecimal taxa) {
        if (taxa == null) {
            throw new IllegalArgumentException("A taxa de câmbio não pode ser nula");
        }
        
        if (taxa.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("A taxa de câmbio deve ser maior que zero");
        }
        
        if (taxa.compareTo(new BigDecimal("1000")) > 0) {
            throw new IllegalArgumentException("A taxa de câmbio não pode ser maior que 1000");
        }
        
        Produto produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            
        Moeda moedaOrigem = moedaRepository.findById(moedaOrigemId)
            .orElseThrow(() -> new RuntimeException("Moeda de origem não encontrada"));
            
        Moeda moedaDestino = moedaRepository.findById(moedaDestinoId)
            .orElseThrow(() -> new RuntimeException("Moeda de destino não encontrada"));
            
        if (moedaOrigem.equals(moedaDestino)) {
            throw new IllegalArgumentException("As moedas de origem e destino devem ser diferentes");
        }
        
        TaxaCambioProduto taxaCambio = taxaCambioProdutoRepository
            .findByProdutoIdAndMoedaOrigemIdAndMoedaDestinoId(produtoId, moedaOrigemId, moedaDestinoId)
            .orElse(new TaxaCambioProduto());
            
        taxaCambio.setProduto(produto);
        taxaCambio.setMoedaOrigem(moedaOrigem);
        taxaCambio.setMoedaDestino(moedaDestino);
        taxaCambio.setTaxa(taxa);
        
        return taxaCambioProdutoRepository.save(taxaCambio);
    }

    @Transactional(readOnly = true)
    public List<TaxaCambioProduto> consultarHistoricoTaxas(
            Long produtoId,
            Long moedaOrigemId,
            Long moedaDestinoId,
            LocalDateTime dataInicio,
            LocalDateTime dataFim) {
        
        Specification<TaxaCambioProduto> spec = Specification.where(null);
        
        if (produtoId != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("produto").get("id"), produtoId));
        }
        
        if (moedaOrigemId != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("moedaOrigem").get("id"), moedaOrigemId));
        }
        
        if (moedaDestinoId != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("moedaDestino").get("id"), moedaDestinoId));
        }
        
        if (dataInicio != null) {
            spec = spec.and((root, query, cb) -> 
                cb.greaterThanOrEqualTo(root.get("dataAtualizacao"), dataInicio));
        }
        
        if (dataFim != null) {
            spec = spec.and((root, query, cb) -> 
                cb.lessThanOrEqualTo(root.get("dataAtualizacao"), dataFim));
        }
        
        return taxaCambioProdutoRepository.findAll(spec);
    }

    @Transactional(readOnly = true)
    public List<Transacao> consultarHistoricoTransacoes(
            Long produtoId,
            Long reinoId,
            Long moedaOrigemId,
            Long moedaDestinoId,
            LocalDateTime dataInicio,
            LocalDateTime dataFim) {
        
        Specification<Transacao> spec = Specification.where(null);
        
        if (produtoId != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("produto").get("id"), produtoId));
        }
        
        if (reinoId != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("reino").get("id"), reinoId));
        }
        
        if (moedaOrigemId != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("moedaOrigem").get("id"), moedaOrigemId));
        }
        
        if (moedaDestinoId != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("moedaDestino").get("id"), moedaDestinoId));
        }
        
        if (dataInicio != null) {
            spec = spec.and((root, query, cb) -> 
                cb.greaterThanOrEqualTo(root.get("dataTransacao"), dataInicio));
        }
        
        if (dataFim != null) {
            spec = spec.and((root, query, cb) -> 
                cb.lessThanOrEqualTo(root.get("dataTransacao"), dataFim));
        }
        
        return transacaoRepository.findAll(spec);
    }

    @Transactional
    public Transacao criarTransacao(TransacaoDTO transacaoDTO) {
        if (transacaoDTO == null) {
            throw new IllegalArgumentException("A transação não pode ser nula");
        }
        
        if (transacaoDTO.getValor() == null || transacaoDTO.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser maior que zero");
        }
        
        Produto produto = produtoRepository.findById(transacaoDTO.getProdutoId())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            
        Reino reino = reinoRepository.findById(transacaoDTO.getReinoId())
            .orElseThrow(() -> new RuntimeException("Reino não encontrado"));
            
        Moeda moedaOrigem = moedaRepository.findById(transacaoDTO.getMoedaOrigemId())
            .orElseThrow(() -> new RuntimeException("Moeda de origem não encontrada"));
            
        Moeda moedaDestino = moedaRepository.findById(transacaoDTO.getMoedaDestinoId())
            .orElseThrow(() -> new RuntimeException("Moeda de destino não encontrada"));
            
        if (moedaOrigem.equals(moedaDestino)) {
            throw new IllegalArgumentException("As moedas de origem e destino devem ser diferentes");
        }
        
        Transacao transacao = new Transacao();
        transacao.setProduto(produto);
        transacao.setReino(reino);
        transacao.setMoedaOrigem(moedaOrigem);
        transacao.setMoedaDestino(moedaDestino);
        transacao.setValor(transacaoDTO.getValor());
        
        return transacaoRepository.save(transacao);
    }
} 