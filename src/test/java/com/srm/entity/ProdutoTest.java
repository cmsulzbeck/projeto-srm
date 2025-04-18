package com.srm.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    void testBuilder() {
        Moeda moeda = new Moeda();
        moeda.setCodigo("USD");

        Produto produto = Produto.builder()
                .id(1L)
                .nome("Laptop")
                .descricao("Laptop Dell")
                .preco(BigDecimal.valueOf(1000.0))
                .quantidadeEstoque(10)
                .moeda(moeda)
                .build();

        assertEquals(1L, produto.getId());
        assertEquals("Laptop", produto.getNome());
        assertEquals("Laptop Dell", produto.getDescricao());
        assertEquals(BigDecimal.valueOf(1000.0), produto.getPreco());
        assertEquals(10, produto.getQuantidadeEstoque());
        assertEquals(moeda, produto.getMoeda());
    }

    @Test
    void testNoArgsConstructor() {
        Produto produto = new Produto();
        assertNotNull(produto);
        assertNull(produto.getId());
        assertNull(produto.getNome());
        assertNull(produto.getDescricao());
        assertNull(produto.getPreco());
        assertNull(produto.getQuantidadeEstoque());
        assertNull(produto.getMoeda());
    }
} 