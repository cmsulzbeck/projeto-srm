package com.srm.service;

import com.srm.dto.MoedaDTO;
import com.srm.exception.MoedaNaoEncontradaException;
import com.srm.mapper.MoedaMapper;
import com.srm.entity.Moeda;
import com.srm.repository.MoedaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoedaServiceTest {

    @Mock
    private MoedaRepository moedaRepository;

    @Mock
    private MoedaMapper moedaMapper;

    @InjectMocks
    private MoedaService moedaService;

    private Moeda moeda;
    private MoedaDTO moedaDTO;

    @BeforeEach
    void setUp() {
        moeda = new Moeda();
        moeda.setId(1L);
        moeda.setNome("Dólar Americano");
        moeda.setCodigo("USD");
        moeda.setTaxaCambio(BigDecimal.ONE);
        moeda.setDataAtualizacao(LocalDateTime.now());

        moedaDTO = new MoedaDTO();
        moedaDTO.setId(1L);
        moedaDTO.setNome("Dólar Americano");
        moedaDTO.setCodigo("USD");
        moedaDTO.setTaxaCambio(BigDecimal.ONE);
    }

    @Test
    void criarMoeda_DeveRetornarMoedaDTO() {
        when(moedaMapper.toEntity(any(MoedaDTO.class))).thenReturn(moeda);
        when(moedaRepository.save(any(Moeda.class))).thenReturn(moeda);
        when(moedaMapper.toDTO(any(Moeda.class))).thenReturn(moedaDTO);

        MoedaDTO resultado = moedaService.criarMoeda(moedaDTO);

        assertNotNull(resultado);
        assertEquals(moedaDTO.getId(), resultado.getId());
        assertEquals(moedaDTO.getNome(), resultado.getNome());
        assertEquals(moedaDTO.getCodigo(), resultado.getCodigo());
        assertEquals(moedaDTO.getTaxaCambio(), resultado.getTaxaCambio());

        verify(moedaMapper).toEntity(moedaDTO);
        verify(moedaRepository).save(moeda);
        verify(moedaMapper).toDTO(moeda);
    }

    @Test
    void atualizarTaxaCambio_QuandoMoedaExiste_DeveRetornarMoedaDTO() {
        BigDecimal novaTaxa = new BigDecimal("1.5");
        when(moedaRepository.findByCodigo("USD")).thenReturn(moeda);
        when(moedaRepository.save(any(Moeda.class))).thenReturn(moeda);
        when(moedaMapper.toDTO(any(Moeda.class))).thenReturn(moedaDTO);

        MoedaDTO resultado = moedaService.atualizarTaxaCambio("USD", novaTaxa);

        assertNotNull(resultado);
        assertEquals(moedaDTO.getId(), resultado.getId());
        assertEquals(novaTaxa, moeda.getTaxaCambio());

        verify(moedaRepository).findByCodigo("USD");
        verify(moedaRepository).save(moeda);
        verify(moedaMapper).toDTO(moeda);
    }

    @Test
    void atualizarTaxaCambio_QuandoMoedaNaoExiste_DeveLancarExcecao() {
        when(moedaRepository.findByCodigo("XXX")).thenReturn(null);

        assertThrows(MoedaNaoEncontradaException.class, () -> {
            moedaService.atualizarTaxaCambio("XXX", BigDecimal.ONE);
        });

        verify(moedaRepository).findByCodigo("XXX");
        verify(moedaRepository, never()).save(any());
    }

    @Test
    void buscarPorCodigo_QuandoMoedaExiste_DeveRetornarMoedaDTO() {
        when(moedaRepository.findByCodigo("USD")).thenReturn(moeda);
        when(moedaMapper.toDTO(any(Moeda.class))).thenReturn(moedaDTO);

        MoedaDTO resultado = moedaService.buscarPorCodigo("USD");

        assertNotNull(resultado);
        assertEquals(moedaDTO.getId(), resultado.getId());
        assertEquals(moedaDTO.getNome(), resultado.getNome());
        assertEquals(moedaDTO.getCodigo(), resultado.getCodigo());

        verify(moedaRepository).findByCodigo("USD");
        verify(moedaMapper).toDTO(moeda);
    }

    @Test
    void buscarPorCodigo_QuandoMoedaNaoExiste_DeveLancarExcecao() {
        when(moedaRepository.findByCodigo("XXX")).thenReturn(null);

        assertThrows(MoedaNaoEncontradaException.class, () -> {
            moedaService.buscarPorCodigo("XXX");
        });

        verify(moedaRepository).findByCodigo("XXX");
        verify(moedaMapper, never()).toDTO(any());
    }

    @Test
    void listarTodas_DeveRetornarListaDeMoedasDTO() {
        List<Moeda> moedas = Arrays.asList(moeda);
        when(moedaRepository.findAll()).thenReturn(moedas);
        when(moedaMapper.toDTO(any(Moeda.class))).thenReturn(moedaDTO);

        List<MoedaDTO> resultado = moedaService.listarTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(moedaDTO.getId(), resultado.get(0).getId());

        verify(moedaRepository).findAll();
        verify(moedaMapper).toDTO(moeda);
    }
} 