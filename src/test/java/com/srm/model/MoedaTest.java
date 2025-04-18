package com.srm.model;

import org.junit.jupiter.api.Test;

import com.srm.entity.Moeda;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MoedaTest {

    @Test
    void testConstrutorVazio() {
        Moeda moeda = new Moeda();
        assertNotNull(moeda);
    }

    @Test
    void testConstrutorComTodosParametros() {
        Long id = 1L;
        String nome = "Dólar";
        String codigo = "USD";
        BigDecimal taxaCambio = BigDecimal.valueOf(5.0);
        LocalDateTime dataAtualizacao = LocalDateTime.now();

        Moeda moeda = new Moeda();
        moeda.setId(id);
        moeda.setNome(nome);
        moeda.setCodigo(codigo);
        moeda.setTaxaCambio(taxaCambio);
        moeda.setDataAtualizacao(dataAtualizacao);

        assertEquals(id, moeda.getId());
        assertEquals(nome, moeda.getNome());
        assertEquals(codigo, moeda.getCodigo());
        assertEquals(taxaCambio, moeda.getTaxaCambio());
        assertEquals(dataAtualizacao, moeda.getDataAtualizacao());
    }

    @Test
    void testEquals() {
        Moeda moeda1 = new Moeda();
        moeda1.setId(1L);
        moeda1.setCodigo("USD");

        Moeda moeda2 = new Moeda();
        moeda2.setId(1L);
        moeda2.setCodigo("USD");

        assertEquals(moeda1, moeda2);
    }

    @Test
    void testNotEquals() {
        Moeda moeda1 = new Moeda();
        moeda1.setId(1L);
        moeda1.setCodigo("USD");

        Moeda moeda2 = new Moeda();
        moeda2.setId(2L);
        moeda2.setCodigo("EUR");

        assertNotEquals(moeda1, moeda2);
    }

    @Test
    void testHashCode() {
        Moeda moeda1 = new Moeda();
        moeda1.setId(1L);
        moeda1.setCodigo("USD");

        Moeda moeda2 = new Moeda();
        moeda2.setId(1L);
        moeda2.setCodigo("USD");

        assertEquals(moeda1.hashCode(), moeda2.hashCode());
    }

    @Test
    void testToString() {
        Moeda moeda = new Moeda();
        moeda.setId(1L);
        moeda.setNome("Dólar");
        moeda.setCodigo("USD");
        moeda.setTaxaCambio(BigDecimal.valueOf(5.0));

        String toString = moeda.toString();
        
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nome=Dólar"));
        assertTrue(toString.contains("codigo=USD"));
        assertTrue(toString.contains("taxaCambio=5.0"));
    }

    @Test
    void testPrePersistUpdate() {
        Moeda moeda = new Moeda();
        assertNull(moeda.getDataAtualizacao());
        
        moeda.prePersistUpdate();
        
        assertNotNull(moeda.getDataAtualizacao());
    }
} 