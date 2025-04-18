package com.srm.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoDTOTest {

    @Test
    void testConstrutorVazio() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        assertNotNull(produtoDTO);
    }

    @Test
    void testConstrutorComTodosParametros() {
        Long id = 1L;
        String nome = "Notebook";
        String descricao = "Notebook Dell";
        BigDecimal preco = BigDecimal.valueOf(5000.0);
        Integer quantidadeEstoque = 10;

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(id);
        produtoDTO.setNome(nome);
        produtoDTO.setDescricao(descricao);
        produtoDTO.setPreco(preco);
        produtoDTO.setQuantidadeEstoque(quantidadeEstoque);

        assertEquals(id, produtoDTO.getId());
        assertEquals(nome, produtoDTO.getNome());
        assertEquals(descricao, produtoDTO.getDescricao());
        assertEquals(preco, produtoDTO.getPreco());
        assertEquals(quantidadeEstoque, produtoDTO.getQuantidadeEstoque());
    }

    @Test
    void testEquals() {
        ProdutoDTO produtoDTO1 = new ProdutoDTO();
        produtoDTO1.setId(1L);
        produtoDTO1.setNome("Notebook");

        ProdutoDTO produtoDTO2 = new ProdutoDTO();
        produtoDTO2.setId(1L);
        produtoDTO2.setNome("Notebook");

        assertEquals(produtoDTO1, produtoDTO2);
    }

    @Test
    void testHashCode() {
        ProdutoDTO produtoDTO1 = new ProdutoDTO();
        produtoDTO1.setId(1L);
        produtoDTO1.setNome("Notebook");

        ProdutoDTO produtoDTO2 = new ProdutoDTO();
        produtoDTO2.setId(1L);
        produtoDTO2.setNome("Notebook");

        assertEquals(produtoDTO1.hashCode(), produtoDTO2.hashCode());
    }

    @Test
    void testToString() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setNome("Notebook");
        produtoDTO.setDescricao("Notebook Dell");
        produtoDTO.setPreco(BigDecimal.valueOf(5000.0));
        produtoDTO.setQuantidadeEstoque(10);

        String toString = produtoDTO.toString();
        
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nome=Notebook"));
        assertTrue(toString.contains("descricao=Notebook Dell"));
        assertTrue(toString.contains("preco=5000.0"));
        assertTrue(toString.contains("quantidadeEstoque=10"));
    }
} 