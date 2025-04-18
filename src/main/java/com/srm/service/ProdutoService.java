package com.srm.service;

import com.srm.dto.ProdutoDTO;
import com.srm.entity.Moeda;
import com.srm.entity.Produto;
import com.srm.exception.ProdutoNaoEncontradoException;
import com.srm.mapper.ProdutoMapper;
import com.srm.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MoedaService moedaService;
    private final ProdutoMapper produtoMapper;

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

    public ProdutoDTO buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .map(produtoMapper::toDTO)
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
        produtoRepository.deleteById(id);
    }
} 