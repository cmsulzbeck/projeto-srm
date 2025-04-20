package com.srm.controller;

import com.srm.dto.TaxaCambioProdutoDTO;
import com.srm.dto.TransacaoDTO;
import com.srm.entity.Transacao;
import com.srm.service.ConversaoService;
import com.srm.mapper.TaxaCambioProdutoMapper;
import com.srm.mapper.TransacaoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/conversoes")
@RequiredArgsConstructor
@Tag(name = "Conversão de Moedas", description = "API para conversão de moedas e produtos")
public class ConversaoController {

    private final ConversaoService conversaoService;
    private final TaxaCambioProdutoMapper taxaCambioProdutoMapper;
    private final TransacaoMapper transacaoMapper;

    @Operation(summary = "Criar nova transação")
    @PostMapping("/transacoes")
    public ResponseEntity<TransacaoDTO> criarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        Transacao transacao = conversaoService.criarTransacao(transacaoDTO);
        return ResponseEntity.ok(transacaoMapper.toDTO(transacao));
    }

    @Operation(summary = "Converter valor entre moedas")
    @GetMapping("/moedas")
    public ResponseEntity<BigDecimal> converterMoedas(
            @RequestParam Long moedaOrigemId,
            @RequestParam Long moedaDestinoId,
            @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(conversaoService.converterMoedas(moedaOrigemId, moedaDestinoId, valor));
    }

    @Operation(summary = "Converter valor de produto entre moedas")
    @GetMapping("/produtos")
    public ResponseEntity<BigDecimal> converterProduto(
            @RequestParam Long produtoId,
            @RequestParam Long moedaOrigemId,
            @RequestParam Long moedaDestinoId,
            @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(conversaoService.converterProduto(produtoId, moedaOrigemId, moedaDestinoId, valor));
    }

    @Operation(summary = "Atualizar taxa de câmbio de produto")
    @PostMapping("/taxas")
    public ResponseEntity<TaxaCambioProdutoDTO> atualizarTaxa(
            @RequestParam Long produtoId,
            @RequestParam Long moedaOrigemId,
            @RequestParam Long moedaDestinoId,
            @RequestParam BigDecimal taxa) {
        return ResponseEntity.ok(
            taxaCambioProdutoMapper.toDTO(
                conversaoService.atualizarTaxa(produtoId, moedaOrigemId, moedaDestinoId, taxa)
            )
        );
    }
} 