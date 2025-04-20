package com.srm.service;

import com.srm.dto.ProdutoDTO;
import com.srm.entity.Moeda;
import com.srm.entity.Produto;
import com.srm.exception.ProdutoNaoEncontradoException;
import com.srm.mapper.ProdutoMapper;
import com.srm.repository.ProdutoRepository;
import com.srm.repository.TaxaCambioProdutoRepository;
import com.srm.repository.FormulasConversaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MoedaService moedaService;
    private final ProdutoMapper produtoMapper;
    private final TaxaCambioProdutoRepository taxaCambioProdutoRepository;
    private final FormulasConversaoRepository formulasConversaoRepository;

    @Transactional
    public ProdutoDTO criar(ProdutoDTO produtoDTO) {
        Moeda moeda = moedaService.buscarEntidadePorCodigo(produtoDTO.getMoedaCodigo());
        if (moeda == null) {
            throw new IllegalArgumentException("Moeda não encontrada: " + produtoDTO.getMoedaCodigo());
        }
        
        Produto produto = produtoMapper.toEntity(produtoDTO);
        produto.setMoeda(moeda);
        
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoMapper.toDTO(produtoSalvo);
    }

    @Transactional
    public ProdutoDTO atualizar(Long id, ProdutoDTO produtoDTO) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        Moeda moeda = moedaService.buscarEntidadePorCodigo(produtoDTO.getMoedaCodigo());
        if (moeda == null) {
            throw new IllegalArgumentException("Moeda não encontrada: " + produtoDTO.getMoedaCodigo());
        }

        produtoMapper.updateEntityFromDTO(produtoDTO, produto);
        produto.setMoeda(moeda);
        
        Produto produtoAtualizado = produtoRepository.save(produto);
        return produtoMapper.toDTO(produtoAtualizado);
    }

    public Optional<ProdutoDTO> buscarPorId(Long id) {
        log.info("Buscando produto por ID: {}", id);
        return produtoRepository.findById(id)
                .map(produtoMapper::toDTO);
    }

    public Produto buscarEntidadePorId(Long id) {
        log.info("Buscando entidade produto por ID: {}", id);
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(produtoMapper::toDTO)
                .toList();
    }

    @Transactional
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNaoEncontradoException(id);
        }
        
        // Primeiro, deletar todas as taxas de câmbio associadas ao produto
        taxaCambioProdutoRepository.deleteByProdutoId(id);
        
        // Depois, deletar todas as fórmulas de conversão associadas ao produto
        formulasConversaoRepository.deleteByProdutoId(id);
        
        // Agora podemos deletar o produto com segurança
        produtoRepository.deleteById(id);
    }

    public ProdutoDTO toDTO(Produto produto) {
        return produtoMapper.toDTO(produto);
    }
} 