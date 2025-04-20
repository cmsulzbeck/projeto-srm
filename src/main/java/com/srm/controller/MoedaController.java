package com.srm.controller;

import com.srm.dto.MoedaDTO;
import com.srm.service.MoedaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/moedas")
@RequiredArgsConstructor
@Tag(name = "Moedas", description = "API para gerenciamento de moedas e taxas de câmbio")
public class MoedaController {

    private final MoedaService moedaService;

    @Operation(summary = "Criar uma nova moeda", description = "Cria uma nova moeda com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Moeda criada com sucesso",
            content = @Content(schema = @Schema(implementation = MoedaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<MoedaDTO> criarMoeda(@RequestBody MoedaDTO moedaDTO) {
        log.info("Recebendo requisição para criar moeda: {}", moedaDTO);
        try {
            MoedaDTO moedaCriada = moedaService.criarMoeda(moedaDTO);
            log.info("Moeda criada com sucesso: {}", moedaCriada);
            return ResponseEntity.created(URI.create("/api/moedas/" + moedaCriada.getCodigo())).body(moedaCriada);
        } catch (Exception e) {
            log.error("Erro ao criar moeda", e);
            throw e;
        }
    }

    @Operation(summary = "Atualizar taxa de câmbio", description = "Atualiza a taxa de câmbio de uma moeda existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Taxa de câmbio atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = MoedaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Moeda não encontrada")
    })
    @PutMapping("/{codigo}/taxa-cambio")
    public ResponseEntity<MoedaDTO> atualizarTaxaCambio(
            @Parameter(description = "Código da moeda", example = "USD")
            @PathVariable String codigo,
            @Parameter(description = "Nova taxa de câmbio", example = "5.25")
            @RequestParam BigDecimal novaTaxa) {
        log.info("Recebendo requisição para atualizar taxa de câmbio da moeda {} para {}", codigo, novaTaxa);
        try {
            MoedaDTO moedaAtualizada = moedaService.atualizarTaxaCambio(codigo, novaTaxa);
            log.info("Taxa de câmbio atualizada com sucesso: {}", moedaAtualizada);
            return ResponseEntity.ok(moedaAtualizada);
        } catch (Exception e) {
            log.error("Erro ao atualizar taxa de câmbio", e);
            throw e;
        }
    }

    @Operation(summary = "Buscar moeda por código", description = "Retorna os dados de uma moeda específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Moeda encontrada",
            content = @Content(schema = @Schema(implementation = MoedaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Moeda não encontrada")
    })
    @GetMapping("/{codigo}")
    public ResponseEntity<MoedaDTO> buscarPorCodigo(
            @Parameter(description = "Código da moeda", example = "USD")
            @PathVariable String codigo) {
        log.info("Recebendo requisição para buscar moeda com código: {}", codigo);
        try {
            return moedaService.buscarPorCodigo(codigo)
                    .map(moeda -> {
                        log.info("Moeda encontrada: {}", moeda);
                        return ResponseEntity.ok(moeda);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Erro ao buscar moeda", e);
            throw e;
        }
    }

    @Operation(summary = "Listar todas as moedas", description = "Retorna uma lista com todas as moedas cadastradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de moedas retornada com sucesso",
            content = @Content(schema = @Schema(implementation = MoedaDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<MoedaDTO>> listarTodas() {
        log.info("Recebendo requisição para listar todas as moedas");
        try {
            List<MoedaDTO> moedas = moedaService.listarTodas();
            log.info("Total de moedas encontradas: {}", moedas.size());
            return ResponseEntity.ok(moedas);
        } catch (Exception e) {
            log.error("Erro ao listar moedas", e);
            throw e;
        }
    }
} 