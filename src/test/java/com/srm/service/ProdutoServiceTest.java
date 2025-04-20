package com.srm.service;

import com.srm.dto.ProdutoDTO;
import com.srm.entity.Moeda;
import com.srm.entity.Produto;
import com.srm.exception.ProdutoNaoEncontradoException;
import com.srm.mapper.ProdutoMapper;
import com.srm.repository.ProdutoRepository;
import com.srm.repository.TaxaCambioProdutoRepository;
import com.srm.repository.FormulasConversaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MoedaService moedaService;

    @Mock
    private ProdutoMapper produtoMapper;

    @Mock
    private TaxaCambioProdutoRepository taxaCambioProdutoRepository;

    @Mock
    private FormulasConversaoRepository formulasConversaoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;
    private ProdutoDTO produtoDTO;
    private Moeda moeda;

    @BeforeEach
    void setUp() {
        moeda = new Moeda();
        moeda.setId(1L);
        moeda.setCodigo("USD");
        moeda.setTaxaCambio(BigDecimal.ONE);

        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook");
        produto.setDescricao("Notebook Dell");
        produto.setPreco(BigDecimal.valueOf(5000.0));
        produto.setQuantidadeEstoque(10);
        produto.setMoeda(moeda);

        produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setNome("Notebook");
        produtoDTO.setDescricao("Notebook Dell");
        produtoDTO.setPreco(BigDecimal.valueOf(5000.0));
        produtoDTO.setMoedaCodigo("USD");
    }

    @Test
    void criar_DeveRetornarProdutoDTO() {
        when(moedaService.buscarEntidadePorCodigo(anyString())).thenReturn(moeda);
        when(produtoMapper.toEntity(any(ProdutoDTO.class))).thenReturn(produto);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);
        when(produtoMapper.toDTO(any(Produto.class))).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoService.criar(produtoDTO);

        assertNotNull(resultado);
        assertEquals(produtoDTO.getId(), resultado.getId());
        assertEquals(produtoDTO.getNome(), resultado.getNome());
        assertEquals(produtoDTO.getDescricao(), resultado.getDescricao());
        assertEquals(produtoDTO.getPreco(), resultado.getPreco());
        assertEquals(produtoDTO.getMoedaCodigo(), resultado.getMoedaCodigo());

        verify(moedaService).buscarEntidadePorCodigo(produtoDTO.getMoedaCodigo());
        verify(produtoMapper).toEntity(produtoDTO);
        verify(produtoRepository).save(produto);
        verify(produtoMapper).toDTO(produto);
    }

    @Test
    void atualizar_QuandoProdutoExiste_DeveRetornarProdutoDTO() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(moedaService.buscarEntidadePorCodigo(anyString())).thenReturn(moeda);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);
        when(produtoMapper.toDTO(any(Produto.class))).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoService.atualizar(1L, produtoDTO);

        assertNotNull(resultado);
        assertEquals(produtoDTO.getId(), resultado.getId());
        assertEquals(produtoDTO.getNome(), resultado.getNome());
        assertEquals(produtoDTO.getDescricao(), resultado.getDescricao());
        assertEquals(produtoDTO.getPreco(), resultado.getPreco());
        assertEquals(produtoDTO.getMoedaCodigo(), resultado.getMoedaCodigo());

        verify(produtoRepository).findById(1L);
        verify(moedaService).buscarEntidadePorCodigo(produtoDTO.getMoedaCodigo());
        verify(produtoRepository).save(produto);
        verify(produtoMapper).toDTO(produto);
    }

    @Test
    void atualizar_QuandoProdutoNaoExiste_DeveLancarExcecao() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> {
            produtoService.atualizar(1L, produtoDTO);
        });

        verify(produtoRepository).findById(1L);
        verify(produtoRepository, never()).save(any());
    }

    @Test
    void buscarPorId_QuandoProdutoExiste_DeveRetornarProdutoDTO() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoMapper.toDTO(any(Produto.class))).thenReturn(produtoDTO);

        Optional<ProdutoDTO> resultado = produtoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(produtoDTO.getId(), resultado.get().getId());
        assertEquals(produtoDTO.getNome(), resultado.get().getNome());
        assertEquals(produtoDTO.getDescricao(), resultado.get().getDescricao());

        verify(produtoRepository).findById(1L);
        verify(produtoMapper).toDTO(produto);
    }

    @Test
    void buscarPorId_QuandoProdutoNaoExiste_DeveRetornarOptionalVazio() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ProdutoDTO> resultado = produtoService.buscarPorId(1L);

        assertFalse(resultado.isPresent());
        verify(produtoRepository).findById(1L);
        verify(produtoMapper, never()).toDTO(any());
    }

    @Test
    void listarTodos_DeveRetornarListaDeProdutosDTO() {
        List<Produto> produtos = Arrays.asList(produto);
        when(produtoRepository.findAll()).thenReturn(produtos);
        when(produtoMapper.toDTO(any(Produto.class))).thenReturn(produtoDTO);

        List<ProdutoDTO> resultado = produtoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(produtoDTO.getId(), resultado.get(0).getId());

        verify(produtoRepository).findAll();
        verify(produtoMapper).toDTO(produto);
    }

    @Test
    void deletar_QuandoProdutoExiste_DeveExecutarComSucesso() {
        when(produtoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taxaCambioProdutoRepository).deleteByProdutoId(1L);
        doNothing().when(formulasConversaoRepository).deleteByProdutoId(1L);
        doNothing().when(produtoRepository).deleteById(1L);

        produtoService.deletar(1L);

        verify(produtoRepository).existsById(1L);
        verify(taxaCambioProdutoRepository).deleteByProdutoId(1L);
        verify(formulasConversaoRepository).deleteByProdutoId(1L);
        verify(produtoRepository).deleteById(1L);
    }

    @Test
    void deletar_QuandoProdutoNaoExiste_DeveLancarExcecao() {
        when(produtoRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProdutoNaoEncontradoException.class, () -> {
            produtoService.deletar(1L);
        });

        verify(produtoRepository).existsById(1L);
        verify(produtoRepository, never()).deleteById(any());
        verify(taxaCambioProdutoRepository, never()).deleteByProdutoId(any());
        verify(formulasConversaoRepository, never()).deleteByProdutoId(any());
    }
} 