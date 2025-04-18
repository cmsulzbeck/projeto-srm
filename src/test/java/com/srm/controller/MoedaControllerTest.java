package com.srm.controller;

import com.srm.dto.MoedaDTO;
import com.srm.exception.MoedaNaoEncontradaException;
import com.srm.service.MoedaService;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoedaControllerTest {

    @Mock
    private MoedaService moedaService;

    @InjectMocks
    private MoedaController moedaController;

    private MoedaDTO moedaDTO;

    @BeforeEach
    void setUp() {
        moedaDTO = new MoedaDTO();
        moedaDTO.setId(1L);
        moedaDTO.setNome("Dólar Americano");
        moedaDTO.setCodigo("USD");
        moedaDTO.setTaxaCambio(BigDecimal.valueOf(5.0));
    }

    @Test
    void criarMoeda_DeveRetornarMoedaDTO() {
        when(moedaService.criarMoeda(any(MoedaDTO.class))).thenReturn(moedaDTO);

        ResponseEntity<MoedaDTO> response = moedaController.criarMoeda(moedaDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        MoedaDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(moedaDTO.getId(), responseBody.getId());
        assertEquals(moedaDTO.getNome(), responseBody.getNome());
        assertEquals(moedaDTO.getCodigo(), responseBody.getCodigo());
        assertEquals(moedaDTO.getTaxaCambio(), responseBody.getTaxaCambio());

        verify(moedaService).criarMoeda(moedaDTO);
    }

    @Test
    void atualizarTaxaCambio_QuandoMoedaExiste_DeveRetornarMoedaDTO() {
        BigDecimal novaTaxa = BigDecimal.valueOf(5.5);
        when(moedaService.atualizarTaxaCambio(anyString(), any(BigDecimal.class))).thenReturn(moedaDTO);

        ResponseEntity<MoedaDTO> response = moedaController.atualizarTaxaCambio("USD", novaTaxa);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        MoedaDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(moedaDTO.getId(), responseBody.getId());
        assertEquals(moedaDTO.getCodigo(), responseBody.getCodigo());

        verify(moedaService).atualizarTaxaCambio("USD", novaTaxa);
    }

    @Test
    void atualizarTaxaCambio_QuandoMoedaNaoExiste_DeveRetornarNotFound() {
        BigDecimal novaTaxa = BigDecimal.valueOf(5.5);
        when(moedaService.atualizarTaxaCambio(anyString(), any(BigDecimal.class)))
            .thenThrow(new MoedaNaoEncontradaException("USD"));

        ResponseEntity<MoedaDTO> response = moedaController.atualizarTaxaCambio("USD", novaTaxa);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(moedaService).atualizarTaxaCambio("USD", novaTaxa);
    }

    @Test
    void buscarPorCodigo_QuandoMoedaExiste_DeveRetornarMoedaDTO() {
        when(moedaService.buscarPorCodigo(anyString())).thenReturn(moedaDTO);

        ResponseEntity<MoedaDTO> response = moedaController.buscarPorCodigo("USD");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        MoedaDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(moedaDTO.getId(), responseBody.getId());
        assertEquals(moedaDTO.getCodigo(), responseBody.getCodigo());

        verify(moedaService).buscarPorCodigo("USD");
    }

    @Test
    void buscarPorCodigo_QuandoMoedaNaoExiste_DeveRetornarNotFound() {
        when(moedaService.buscarPorCodigo(anyString())).thenReturn(null);

        ResponseEntity<MoedaDTO> response = moedaController.buscarPorCodigo("XYZ");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(moedaService).buscarPorCodigo("XYZ");
    }

    @Test
    void listarTodas_QuandoExistemMoedas_DeveRetornarListaDeMoedasDTO() {
        List<MoedaDTO> moedas = Arrays.asList(moedaDTO);
        when(moedaService.listarTodas()).thenReturn(moedas);

        ResponseEntity<List<MoedaDTO>> response = moedaController.listarTodas();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<MoedaDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.size());
        assertEquals(moedaDTO.getId(), responseBody.get(0).getId());
        assertEquals(moedaDTO.getCodigo(), responseBody.get(0).getCodigo());

        verify(moedaService).listarTodas();
    }

    @Test
    void listarTodas_QuandoNaoExistemMoedas_DeveRetornarListaVazia() {
        when(moedaService.listarTodas()).thenReturn(Collections.emptyList());

        ResponseEntity<List<MoedaDTO>> response = moedaController.listarTodas();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<MoedaDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.isEmpty());

        verify(moedaService).listarTodas();
    }
} 