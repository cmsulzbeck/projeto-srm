package com.srm.service;

import com.srm.dto.TransacaoDTO;
import com.srm.dto.ProdutoDTO;
import com.srm.entity.Moeda;
import com.srm.entity.Produto;
import com.srm.entity.Reino;
import com.srm.entity.Transacao;
import com.srm.exception.ProdutoNaoEncontradoException;
import com.srm.exception.ReinoNaoEncontradoException;
import com.srm.exception.TransacaoException;
import com.srm.mapper.TransacaoMapper;
import com.srm.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private TransacaoMapper transacaoMapper;

    @Mock
    private MoedaService moedaService;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private ReinoService reinoService;

    @InjectMocks
    private TransacaoService transacaoService;

    private TransacaoDTO transacaoDTO;
    private Transacao transacao;
    private Moeda moedaOrigem;
    private Moeda moedaDestino;
    private Produto produto;
    private Reino reino;

    @BeforeEach
    void setUp() {
        // Configurar moedas
        moedaOrigem = new Moeda();
        moedaOrigem.setId(1L);
        moedaOrigem.setCodigo("USD");
        moedaOrigem.setTaxaCambio(BigDecimal.ONE);

        moedaDestino = new Moeda();
        moedaDestino.setId(2L);
        moedaDestino.setCodigo("BRL");
        moedaDestino.setTaxaCambio(BigDecimal.valueOf(5.0));

        // Configurar produto
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook");
        produto.setPreco(BigDecimal.valueOf(1000.0));
        produto.setQuantidadeEstoque(10);
        produto.setMoeda(moedaOrigem);

        // Configurar reino
        reino = new Reino();
        reino.setId(1L);
        reino.setNome("Reino do Norte");

        // Configurar transação
        transacao = new Transacao();
        transacao.setId(1L);
        transacao.setTipoTransacao(com.srm.enums.TipoTransacao.COMPRA);
        transacao.setValor(BigDecimal.valueOf(5000.0));
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setDescricao("Compra de notebook");
        transacao.setProduto(produto);
        transacao.setMoedaOrigem(moedaOrigem);
        transacao.setMoedaDestino(moedaDestino);
        transacao.setReino(reino);

        // Configurar DTO
        transacaoDTO = new TransacaoDTO();
        transacaoDTO.setId(1L);
        transacaoDTO.setTipoTransacao(com.srm.enums.TipoTransacao.COMPRA);
        transacaoDTO.setValor(BigDecimal.valueOf(5000.0));
        transacaoDTO.setDataTransacao(LocalDateTime.now());
        transacaoDTO.setDescricao("Compra de notebook");
        transacaoDTO.setProdutoId(1L);
        transacaoDTO.setMoedaOrigemCodigo("USD");
        transacaoDTO.setMoedaDestinoCodigo("BRL");
        transacaoDTO.setReinoId(1L);
    }

    @Test
    void criarTransacao_QuandoDadosValidos_DeveRetornarTransacaoDTO() {
        when(transacaoMapper.toEntity(any(TransacaoDTO.class))).thenReturn(transacao);
        when(moedaService.buscarEntidadePorCodigo("USD")).thenReturn(moedaOrigem);
        when(moedaService.buscarEntidadePorCodigo("BRL")).thenReturn(moedaDestino);
        when(produtoService.buscarEntidadePorId(1L)).thenReturn(produto);
        when(reinoService.buscarEntidadePorId(1L)).thenReturn(reino);
        when(produtoService.toDTO(any(Produto.class))).thenReturn(new ProdutoDTO());
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);
        when(transacaoMapper.toDTO(any(Transacao.class))).thenReturn(transacaoDTO);

        TransacaoDTO resultado = transacaoService.criarTransacao(transacaoDTO);

        assertNotNull(resultado);
        assertEquals(transacaoDTO.getId(), resultado.getId());
        assertEquals(transacaoDTO.getTipoTransacao(), resultado.getTipoTransacao());
        assertEquals(transacaoDTO.getValor(), resultado.getValor());

        verify(transacaoMapper).toEntity(transacaoDTO);
        verify(moedaService).buscarEntidadePorCodigo("USD");
        verify(moedaService).buscarEntidadePorCodigo("BRL");
        verify(produtoService).buscarEntidadePorId(1L);
        verify(reinoService).buscarEntidadePorId(1L);
        verify(transacaoRepository).save(transacao);
        verify(transacaoMapper).toDTO(transacao);
    }

    @Test
    void criarTransacao_QuandoProdutoNaoExiste_DeveLancarTransacaoException() {
        when(transacaoMapper.toEntity(any(TransacaoDTO.class))).thenReturn(transacao);
        when(moedaService.buscarEntidadePorCodigo("USD")).thenReturn(moedaOrigem);
        when(moedaService.buscarEntidadePorCodigo("BRL")).thenReturn(moedaDestino);
        when(produtoService.buscarEntidadePorId(1L)).thenThrow(new ProdutoNaoEncontradoException(1L));

        TransacaoException ex = assertThrows(TransacaoException.class, () -> {
            transacaoService.criarTransacao(transacaoDTO);
        });

        assertEquals("Produto com ID 1 não encontrado no banco de dados", ex.getMessage());
    }

    @Test
    void criarTransacao_QuandoReinoNaoExiste_DeveLancarTransacaoException() {
        when(transacaoMapper.toEntity(any(TransacaoDTO.class))).thenReturn(transacao);
        when(moedaService.buscarEntidadePorCodigo("USD")).thenReturn(moedaOrigem);
        when(moedaService.buscarEntidadePorCodigo("BRL")).thenReturn(moedaDestino);
        when(produtoService.buscarEntidadePorId(1L)).thenReturn(produto);
        when(reinoService.buscarEntidadePorId(1L)).thenThrow(new ReinoNaoEncontradoException(1L));

        TransacaoException ex = assertThrows(TransacaoException.class, () -> {
            transacaoService.criarTransacao(transacaoDTO);
        });

        assertEquals("Reino com ID 1 não encontrado no banco de dados", ex.getMessage());
    }

    @Test
    void criarTransacao_QuandoEstoqueInsuficiente_DeveLancarTransacaoException() {
        produto.setQuantidadeEstoque(0);
        
        when(transacaoMapper.toEntity(any(TransacaoDTO.class))).thenReturn(transacao);
        when(moedaService.buscarEntidadePorCodigo("USD")).thenReturn(moedaOrigem);
        when(moedaService.buscarEntidadePorCodigo("BRL")).thenReturn(moedaDestino);
        when(produtoService.buscarEntidadePorId(1L)).thenReturn(produto);
        when(reinoService.buscarEntidadePorId(1L)).thenReturn(reino);

        TransacaoException ex = assertThrows(TransacaoException.class, () -> {
            transacaoService.criarTransacao(transacaoDTO);
        });

        assertEquals("Estoque insuficiente para o produto 'Notebook' (ID: 1)", ex.getMessage());
    }

    @Test
    void buscarPorId_QuandoTransacaoExiste_DeveRetornarTransacaoDTO() {
        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(transacao));
        when(transacaoMapper.toDTO(any(Transacao.class))).thenReturn(transacaoDTO);

        TransacaoDTO resultado = transacaoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(transacaoDTO.getId(), resultado.getId());
        assertEquals(transacaoDTO.getTipoTransacao(), resultado.getTipoTransacao());
        assertEquals(transacaoDTO.getValor(), resultado.getValor());

        verify(transacaoRepository).findById(1L);
        verify(transacaoMapper).toDTO(transacao);
    }

    @Test
    void buscarPorId_QuandoTransacaoNaoExiste_DeveLancarTransacaoException() {
        when(transacaoRepository.findById(1L)).thenReturn(Optional.empty());

        TransacaoException ex = assertThrows(TransacaoException.class, () -> {
            transacaoService.buscarPorId(1L);
        });

        assertEquals("Transação não encontrada com ID: 1", ex.getMessage());
    }

    @Test
    void listarTodas_DeveRetornarListaDeTransacoesDTO() {
        List<Transacao> transacoes = List.of(transacao);
        when(transacaoRepository.findAll()).thenReturn(transacoes);
        when(transacaoMapper.toDTO(any(Transacao.class))).thenReturn(transacaoDTO);

        List<TransacaoDTO> resultado = transacaoService.listarTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(transacaoDTO.getId(), resultado.get(0).getId());

        verify(transacaoRepository).findAll();
        verify(transacaoMapper).toDTO(transacao);
    }
} 