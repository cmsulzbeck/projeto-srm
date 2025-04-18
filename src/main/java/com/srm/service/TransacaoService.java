package com.srm.service;

import com.srm.dto.TransacaoDTO;
import com.srm.exception.TransacaoNaoEncontradaException;
import com.srm.mapper.TransacaoMapper;
import com.srm.entity.Produto;
import com.srm.entity.Transacao;
import com.srm.entity.TipoTransacao;
import com.srm.repository.ProdutoRepository;
import com.srm.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final TransacaoMapper transacaoMapper;

    public List<TransacaoDTO> listarTodas() {
        log.info("Listando todas as transações");
        
        List<TransacaoDTO> transacoes = transacaoRepository.findAll().stream()
                .map(transacaoMapper::toDTO)
                .collect(Collectors.toList());
        
        log.info("Total de transações encontradas: {}", transacoes.size());
        return transacoes;
    }

    public TransacaoDTO buscarPorId(Long id) {
        log.info("Buscando transação por ID: {}", id);
        
        return transacaoRepository.findById(id)
                .map(transacao -> {
                    log.info("Transação encontrada: {}", transacao);
                    return transacaoMapper.toDTO(transacao);
                })
                .orElseThrow(() -> new TransacaoNaoEncontradaException(id));
    }

    @Transactional
    public TransacaoDTO registrarTransacao(TransacaoDTO dto) {
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Validar e atualizar estoque
        if (dto.getTipo().equals("SAIDA")) {
            if (produto.getQuantidadeEstoque() < dto.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente");
            }
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - dto.getQuantidade());
        } else if (dto.getTipo().equals("ENTRADA")) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + dto.getQuantidade());
        } else {
            throw new RuntimeException("Tipo de transação inválido");
        }

        // Calcular valor total
        BigDecimal valorTotalBigDecimal = produto.getPreco().multiply(BigDecimal.valueOf(dto.getQuantidade()));
        Double valorTotal = valorTotalBigDecimal.doubleValue();

        // Criar e salvar a transação
        Transacao transacao = new Transacao();
        transacao.setProduto(produto);
        transacao.setQuantidade(dto.getQuantidade());
        transacao.setValorTotal(valorTotal);
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setTipo(TipoTransacao.valueOf(dto.getTipo()));

        // Salvar as alterações
        produtoRepository.save(produto);
        transacao = transacaoRepository.save(transacao);

        // Converter para DTO e retornar
        return transacaoMapper.toDTO(transacao);
    }
} 