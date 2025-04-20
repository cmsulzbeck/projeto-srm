package com.srm.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;

import com.srm.dto.TransacaoDTO;
import com.srm.entity.Moeda;
import com.srm.entity.Produto;
import com.srm.entity.Reino;
import com.srm.entity.Transacao;
import com.srm.exception.ProdutoNaoEncontradoException;
import com.srm.exception.ReinoNaoEncontradoException;
import com.srm.exception.TransacaoException;
import com.srm.mapper.TransacaoMapper;
import com.srm.repository.TransacaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final TransacaoMapper transacaoMapper;
    private final MoedaService moedaService;
    private final ProdutoService produtoService;
    private final ReinoService reinoService;

    @Transactional
    public TransacaoDTO criarTransacao(TransacaoDTO transacaoDTO) {
        log.info("Iniciando criação de nova transação: {}", transacaoDTO);
        
        try {
            Transacao transacao = transacaoMapper.toEntity(transacaoDTO);
            
            // Buscar moedas
            Moeda moedaOrigem = moedaService.buscarEntidadePorCodigo(transacaoDTO.getMoedaOrigemCodigo());
            Moeda moedaDestino = moedaService.buscarEntidadePorCodigo(transacaoDTO.getMoedaDestinoCodigo());
            
            // Buscar produto
            Produto produto;
            try {
                produto = produtoService.buscarEntidadePorId(transacaoDTO.getProdutoId());
            } catch (ProdutoNaoEncontradoException e) {
                throw new TransacaoException("Produto com ID " + transacaoDTO.getProdutoId() + " não encontrado no banco de dados");
            }
            
            // Buscar reino
            Reino reino;
            try {
                reino = reinoService.buscarEntidadePorId(transacaoDTO.getReinoId());
            } catch (ReinoNaoEncontradoException e) {
                throw new TransacaoException("Reino com ID " + transacaoDTO.getReinoId() + " não encontrado no banco de dados");
            }
            
            // Verificar estoque
            if (produto.getQuantidadeEstoque() < 1) {
                throw new TransacaoException("Estoque insuficiente para o produto '" + produto.getNome() + "' (ID: " + produto.getId() + ")");
            }
            
            // Atualizar estoque
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - 1);
            produtoService.atualizar(produto.getId(), produtoService.toDTO(produto));
            
            // Calcular valor convertido
            BigDecimal valorOriginal = produto.getPreco();
            BigDecimal taxaCambio = moedaDestino.getTaxaCambio().divide(moedaOrigem.getTaxaCambio(), 2, RoundingMode.HALF_UP);
            BigDecimal valorConvertido = valorOriginal.multiply(taxaCambio);
            
            // Configurar transação
            transacao.setMoedaOrigem(moedaOrigem);
            transacao.setMoedaDestino(moedaDestino);
            transacao.setProduto(produto);
            transacao.setReino(reino);
            transacao.setValor(valorConvertido);
            transacao.setDataTransacao(LocalDateTime.now());
            
            Transacao transacaoSalva = transacaoRepository.save(transacao);
            log.info("Transação criada com sucesso: {}", transacaoSalva);
            return transacaoMapper.toDTO(transacaoSalva);
            
        } catch (TransacaoException e) {
            log.error("Erro de negócio ao criar transação: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao criar transação: {}", e.getMessage());
            throw new TransacaoException("Ocorreu um erro inesperado ao criar a transação");
        }
    }

    public TransacaoDTO buscarPorId(Long id) {
        log.info("Buscando transação por ID: {}", id);
        return transacaoRepository.findById(id)
                .map(transacaoMapper::toDTO)
                .orElseThrow(() -> new TransacaoException("Transação não encontrada com ID: " + id));
    }

    public List<TransacaoDTO> listarTodas() {
        log.info("Listando todas as transações");
        return transacaoRepository.findAll().stream()
                .map(transacaoMapper::toDTO)
                .toList();
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
} 