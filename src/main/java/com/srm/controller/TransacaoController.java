package com.srm.controller;

import com.srm.dto.TransacaoDTO;
import com.srm.entity.Transacao;
import com.srm.service.TransacaoService;
import com.srm.mapper.TransacaoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
@Tag(name = "Transações", description = "API para gerenciamento de transações comerciais")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final TransacaoMapper transacaoMapper;

    @Operation(summary = "Consultar histórico de transações")
    @GetMapping("/historico")
    public ResponseEntity<List<TransacaoDTO>> consultarHistoricoTransacoes(
            @RequestParam(required = false) Long produtoId,
            @RequestParam(required = false) Long reinoId,
            @RequestParam(required = false) Long moedaOrigemId,
            @RequestParam(required = false) Long moedaDestinoId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        List<Transacao> transacoes = transacaoService.consultarHistoricoTransacoes(
            produtoId, reinoId, moedaOrigemId, moedaDestinoId, dataInicio, dataFim);
        return ResponseEntity.ok(transacoes.stream()
            .map(transacaoMapper::toDTO)
            .collect(Collectors.toList()));
    }

    @Operation(summary = "Criar nova transação")
    @PostMapping
    public ResponseEntity<TransacaoDTO> criarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        TransacaoDTO transacaoCriada = transacaoService.criarTransacao(transacaoDTO);
        return ResponseEntity.ok(transacaoCriada);
    }

    @Operation(summary = "Buscar transação por ID", description = "Retorna os dados de uma transação específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transação encontrada",
            content = @Content(schema = @Schema(implementation = TransacaoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDTO> buscarPorId(
            @Parameter(description = "ID da transação", example = "1")
            @PathVariable Long id) {
        log.info("Recebendo requisição para buscar transação com id: {}", id);
        try {
            TransacaoDTO transacao = transacaoService.buscarPorId(id);
            log.info("Transação encontrada: {}", transacao);
            return ResponseEntity.ok(transacao);
        } catch (Exception e) {
            log.error("Erro ao buscar transação", e);
            throw e;
        }
    }

    @Operation(summary = "Listar todas as transações", description = "Retorna uma lista com todas as transações cadastradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de transações retornada com sucesso",
            content = @Content(schema = @Schema(implementation = TransacaoDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<TransacaoDTO>> listarTodas() {
        log.info("Recebendo requisição para listar todas as transações");
        try {
            List<TransacaoDTO> transacoes = transacaoService.listarTodas();
            log.info("Total de transações encontradas: {}", transacoes.size());
            return ResponseEntity.ok(transacoes);
        } catch (Exception e) {
            log.error("Erro ao listar transações", e);
            throw e;
        }
    }
} 