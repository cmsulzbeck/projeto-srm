package com.srm.mapper;

import com.srm.dto.ProdutoDTO;
import com.srm.entity.Produto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

class ProdutoMapperTest {

    private ProdutoMapper mapper = Mappers.getMapper(ProdutoMapper.class);

    @Test
    void testToDTO() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook");
        produto.setDescricao("Notebook Dell");
        produto.setPreco(BigDecimal.valueOf(5000.0));
        produto.setQuantidadeEstoque(10);

        ProdutoDTO dto = mapper.toDTO(produto);

        assertNotNull(dto);
        assertEquals(produto.getId(), dto.getId());
        assertEquals(produto.getNome(), dto.getNome());
        assertEquals(produto.getDescricao(), dto.getDescricao());
        assertEquals(produto.getPreco(), dto.getPreco());
        assertEquals(produto.getQuantidadeEstoque(), dto.getQuantidadeEstoque());
    }

    @Test
    void testToEntity() {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(1L);
        dto.setNome("Notebook");
        dto.setDescricao("Notebook Dell");
        dto.setPreco(BigDecimal.valueOf(5000.0));
        dto.setQuantidadeEstoque(10);

        Produto produto = mapper.toEntity(dto);

        assertNotNull(produto);
        assertEquals(dto.getId(), produto.getId());
        assertEquals(dto.getNome(), produto.getNome());
        assertEquals(dto.getDescricao(), produto.getDescricao());
        assertEquals(dto.getPreco(), produto.getPreco());
        assertEquals(dto.getQuantidadeEstoque(), produto.getQuantidadeEstoque());
    }

    @Test
    void testToDTOWithNull() {
        assertNull(mapper.toDTO(null));
    }

    @Test
    void testToEntityWithNull() {
        assertNull(mapper.toEntity(null));
    }
} 