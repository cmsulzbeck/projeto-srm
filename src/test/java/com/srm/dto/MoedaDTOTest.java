package com.srm.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MoedaDTOTest {

    @Test
    void testConstrutorVazio() {
        MoedaDTO moedaDTO = new MoedaDTO();
        assertNotNull(moedaDTO);
    }

    @Test
    void testConstrutorComTodosParametros() {
        Long id = 1L;
        String nome = "Dólar";
        String codigo = "USD";
        BigDecimal taxaCambio = BigDecimal.valueOf(5.0);
        LocalDateTime dataAtualizacao = LocalDateTime.now();

        MoedaDTO moedaDTO = new MoedaDTO();
        moedaDTO.setId(id);
        moedaDTO.setNome(nome);
        moedaDTO.setCodigo(codigo);
        moedaDTO.setTaxaCambio(taxaCambio);
        moedaDTO.setDataAtualizacao(dataAtualizacao);

        assertEquals(id, moedaDTO.getId());
        assertEquals(nome, moedaDTO.getNome());
        assertEquals(codigo, moedaDTO.getCodigo());
        assertEquals(taxaCambio, moedaDTO.getTaxaCambio());
        assertEquals(dataAtualizacao, moedaDTO.getDataAtualizacao());
    }

    @Test
    void testEquals() {
        MoedaDTO moedaDTO1 = new MoedaDTO();
        moedaDTO1.setId(1L);
        moedaDTO1.setCodigo("USD");

        MoedaDTO moedaDTO2 = new MoedaDTO();
        moedaDTO2.setId(1L);
        moedaDTO2.setCodigo("USD");

        assertEquals(moedaDTO1, moedaDTO2);
    }

    @Test
    void testHashCode() {
        MoedaDTO moedaDTO1 = new MoedaDTO();
        moedaDTO1.setId(1L);
        moedaDTO1.setCodigo("USD");

        MoedaDTO moedaDTO2 = new MoedaDTO();
        moedaDTO2.setId(1L);
        moedaDTO2.setCodigo("USD");

        assertEquals(moedaDTO1.hashCode(), moedaDTO2.hashCode());
    }

    @Test
    void testToString() {
        MoedaDTO moedaDTO = new MoedaDTO();
        moedaDTO.setId(1L);
        moedaDTO.setNome("Dólar");
        moedaDTO.setCodigo("USD");
        moedaDTO.setTaxaCambio(BigDecimal.valueOf(5.0));

        String toString = moedaDTO.toString();
        
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nome=Dólar"));
        assertTrue(toString.contains("codigo=USD"));
        assertTrue(toString.contains("taxaCambio=5.0"));
    }
} 