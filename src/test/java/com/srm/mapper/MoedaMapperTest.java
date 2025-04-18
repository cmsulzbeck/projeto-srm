package com.srm.mapper;

import com.srm.dto.MoedaDTO;
import com.srm.entity.Moeda;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MoedaMapperTest {

    private MoedaMapper mapper = Mappers.getMapper(MoedaMapper.class);

    @Test
    void testToDTO() {
        Moeda moeda = new Moeda();
        moeda.setId(1L);
        moeda.setNome("Dólar");
        moeda.setCodigo("USD");
        moeda.setTaxaCambio(BigDecimal.valueOf(5.0));
        moeda.setDataAtualizacao(LocalDateTime.now());

        MoedaDTO dto = mapper.toDTO(moeda);

        assertNotNull(dto);
        assertEquals(moeda.getId(), dto.getId());
        assertEquals(moeda.getNome(), dto.getNome());
        assertEquals(moeda.getCodigo(), dto.getCodigo());
        assertEquals(moeda.getTaxaCambio(), dto.getTaxaCambio());
        assertEquals(moeda.getDataAtualizacao(), dto.getDataAtualizacao());
    }

    @Test
    void testToEntity() {
        MoedaDTO dto = new MoedaDTO();
        dto.setId(1L);
        dto.setNome("Dólar");
        dto.setCodigo("USD");
        dto.setTaxaCambio(BigDecimal.valueOf(5.0));
        dto.setDataAtualizacao(LocalDateTime.now());

        Moeda moeda = mapper.toEntity(dto);

        assertNotNull(moeda);
        assertEquals(dto.getId(), moeda.getId());
        assertEquals(dto.getNome(), moeda.getNome());
        assertEquals(dto.getCodigo(), moeda.getCodigo());
        assertEquals(dto.getTaxaCambio(), moeda.getTaxaCambio());
        assertEquals(dto.getDataAtualizacao(), moeda.getDataAtualizacao());
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