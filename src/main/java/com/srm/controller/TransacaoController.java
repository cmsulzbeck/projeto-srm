package com.srm.controller;

import com.srm.dto.TransacaoDTO;
import com.srm.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/transacoes")
@Tag(name = "Transações", description = "API para gerenciamento de transações")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @GetMapping
    @Operation(summary = "Listar todas as transações")
    public ResponseEntity<List<TransacaoDTO>> listarTodas() {
        log.info("Listando todas as transações");
        List<TransacaoDTO> transacoes = transacaoService.listarTodas();
        log.info("Total de transações encontradas: {}", transacoes.size());
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar transação por ID")
    public ResponseEntity<TransacaoDTO> buscarPorId(@PathVariable Long id) {
        log.info("Buscando transação por ID: {}", id);
        TransacaoDTO transacao = transacaoService.buscarPorId(id);
        log.info("Transação encontrada: {}", transacao);
        return ResponseEntity.ok(transacao);
    }

    @PostMapping
    @Operation(summary = "Registrar nova transação")
    public ResponseEntity<TransacaoDTO> registrarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        log.info("Registrando nova transação: {}", transacaoDTO);
        TransacaoDTO transacaoRegistrada = transacaoService.registrarTransacao(transacaoDTO);
        log.info("Transação registrada com sucesso: {}", transacaoRegistrada);
        return ResponseEntity.ok(transacaoRegistrada);
    }
} 