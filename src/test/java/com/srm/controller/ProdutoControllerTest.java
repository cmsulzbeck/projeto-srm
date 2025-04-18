package com.srm.controller;

import com.srm.dto.ProdutoDTO;
import com.srm.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoControllerTest {

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    private ProdutoDTO produtoDTO;

    @BeforeEach
    void setUp() {
        produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setNome("Notebook");
        produtoDTO.setDescricao("Notebook Dell");
        produtoDTO.setPreco(BigDecimal.valueOf(5000.0));
        produtoDTO.setQuantidadeEstoque(10);
    }

    @Test
    void criarProduto_DeveRetornarProdutoDTO() {
        when(produtoService.criar(any(ProdutoDTO.class))).thenReturn(produtoDTO);

        ResponseEntity<ProdutoDTO> response = produtoController.criarProduto(produtoDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ProdutoDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(produtoDTO.getId(), responseBody.getId());
        assertEquals(produtoDTO.getNome(), responseBody.getNome());
        assertEquals(produtoDTO.getDescricao(), responseBody.getDescricao());
        assertEquals(produtoDTO.getPreco(), responseBody.getPreco());
        assertEquals(produtoDTO.getQuantidadeEstoque(), responseBody.getQuantidadeEstoque());

        verify(produtoService).criar(produtoDTO);
    }

    @Test
    void atualizarProduto_QuandoProdutoExiste_DeveRetornarProdutoDTO() {
        when(produtoService.atualizar(eq(1L), any(ProdutoDTO.class))).thenReturn(produtoDTO);

        ResponseEntity<ProdutoDTO> response = produtoController.atualizarProduto(1L, produtoDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ProdutoDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(produtoDTO.getId(), responseBody.getId());
        assertEquals(produtoDTO.getNome(), responseBody.getNome());
        assertEquals(produtoDTO.getDescricao(), responseBody.getDescricao());
        assertEquals(produtoDTO.getPreco(), responseBody.getPreco());
        assertEquals(produtoDTO.getQuantidadeEstoque(), responseBody.getQuantidadeEstoque());

        verify(produtoService).atualizar(1L, produtoDTO);
    }

    @Test
    void buscarPorId_QuandoProdutoExiste_DeveRetornarProdutoDTO() {
        when(produtoService.buscarPorId(1L)).thenReturn(produtoDTO);

        ResponseEntity<ProdutoDTO> response = produtoController.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ProdutoDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(produtoDTO.getId(), responseBody.getId());
        assertEquals(produtoDTO.getNome(), responseBody.getNome());
        assertEquals(produtoDTO.getDescricao(), responseBody.getDescricao());
        assertEquals(produtoDTO.getPreco(), responseBody.getPreco());
        assertEquals(produtoDTO.getQuantidadeEstoque(), responseBody.getQuantidadeEstoque());

        verify(produtoService).buscarPorId(1L);
    }

    @Test
    void listarTodos_DeveRetornarListaDeProdutosDTO() {
        List<ProdutoDTO> produtos = Arrays.asList(produtoDTO);
        when(produtoService.listarTodos()).thenReturn(produtos);

        ResponseEntity<List<ProdutoDTO>> response = produtoController.listarTodos();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ProdutoDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.size());
        assertEquals(produtoDTO.getId(), responseBody.get(0).getId());
        assertEquals(produtoDTO.getNome(), responseBody.get(0).getNome());
        assertEquals(produtoDTO.getDescricao(), responseBody.get(0).getDescricao());
        assertEquals(produtoDTO.getPreco(), responseBody.get(0).getPreco());
        assertEquals(produtoDTO.getQuantidadeEstoque(), responseBody.get(0).getQuantidadeEstoque());

        verify(produtoService).listarTodos();
    }

    @Test
    void deletarProduto_QuandoProdutoExiste_DeveRetornarNoContent() {
        doNothing().when(produtoService).deletar(1L);

        ResponseEntity<Void> response = produtoController.deletarProduto(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(produtoService).deletar(1L);
    }
} 