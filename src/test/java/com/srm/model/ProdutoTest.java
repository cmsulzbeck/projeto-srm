package com.srm.model;

import org.junit.jupiter.api.Test;

import com.srm.entity.Produto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

class ProdutoTest {

    @Test
    void testConstrutorVazio() {
        Produto produto = new Produto();
        assertNotNull(produto);
    }

    @Test
    void testConstrutorComTodosParametros() {
        Long id = 1L;
        String nome = "Notebook";
        String descricao = "Notebook Dell";
        BigDecimal preco = BigDecimal.valueOf(5000.0);
        Integer quantidadeEstoque = 10;

        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidadeEstoque(quantidadeEstoque);

        assertEquals(id, produto.getId());
        assertEquals(nome, produto.getNome());
        assertEquals(descricao, produto.getDescricao());
        assertEquals(preco, produto.getPreco());
        assertEquals(quantidadeEstoque, produto.getQuantidadeEstoque());
    }

    @Test
    void testEquals() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Notebook");

        Produto produto2 = new Produto();
        produto2.setId(1L);
        produto2.setNome("Notebook");

        assertEquals(produto1, produto2);
    }

    @Test
    void testNotEquals() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Notebook");

        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Mouse");

        assertNotEquals(produto1, produto2);
    }

    @Test
    void testHashCode() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Notebook");

        Produto produto2 = new Produto();
        produto2.setId(1L);
        produto2.setNome("Notebook");

        assertEquals(produto1.hashCode(), produto2.hashCode());
    }

    @Test
    void testToString() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook");
        produto.setDescricao("Notebook Dell");
        produto.setPreco(BigDecimal.valueOf(5000.0));
        produto.setQuantidadeEstoque(10);

        String toString = produto.toString();
        
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nome=Notebook"));
        assertTrue(toString.contains("descricao=Notebook Dell"));
        assertTrue(toString.contains("preco=5000.0"));
        assertTrue(toString.contains("quantidadeEstoque=10"));
    }
} 