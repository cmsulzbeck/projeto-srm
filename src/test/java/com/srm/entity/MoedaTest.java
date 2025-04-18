package com.srm.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class MoedaTest {

    @Test
    void testBuilder() {
        LocalDateTime now = LocalDateTime.now();
        Moeda moeda = Moeda.builder()
                .id(1L)
                .nome("Dólar")
                .codigo("USD")
                .taxaCambio(BigDecimal.valueOf(5.0))
                .dataAtualizacao(now)
                .build();

        assertEquals(1L, moeda.getId());
        assertEquals("Dólar", moeda.getNome());
        assertEquals("USD", moeda.getCodigo());
        assertEquals(BigDecimal.valueOf(5.0), moeda.getTaxaCambio());
        assertEquals(now, moeda.getDataAtualizacao());
    }

    @Test
    void testPrePersist() {
        Moeda moeda = new Moeda();
        assertNull(moeda.getDataAtualizacao());
        
        moeda.prePersistUpdate();
        assertNotNull(moeda.getDataAtualizacao());
    }

    @Test
    void testPreUpdate() {
        Moeda moeda = new Moeda();
        LocalDateTime oldDate = LocalDateTime.now().minusDays(1);
        moeda.setDataAtualizacao(oldDate);
        
        moeda.prePersistUpdate();
        assertNotEquals(oldDate, moeda.getDataAtualizacao());
        assertTrue(moeda.getDataAtualizacao().isAfter(oldDate));
    }
} 