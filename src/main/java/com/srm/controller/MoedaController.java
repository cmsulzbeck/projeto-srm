package com.srm.controller;

import com.srm.dto.MoedaDTO;
import com.srm.exception.MoedaNaoEncontradaException;
import com.srm.service.MoedaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/moedas")
@Tag(name = "Moedas", description = "API para gerenciamento de moedas e taxas de câmbio")
public class MoedaController {

    @Autowired
    private MoedaService moedaService;

    @PostMapping
    @Operation(summary = "Criar uma nova moeda")
    public ResponseEntity<MoedaDTO> criarMoeda(@RequestBody MoedaDTO moedaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(moedaService.criarMoeda(moedaDTO));
    }

    @PutMapping("/{codigo}/taxa")
    @Operation(summary = "Atualizar taxa de câmbio de uma moeda")
    public ResponseEntity<MoedaDTO> atualizarTaxaCambio(
            @PathVariable String codigo,
            @RequestParam BigDecimal novaTaxa) {
        try {
            MoedaDTO moedaDTO = moedaService.atualizarTaxaCambio(codigo, novaTaxa);
            return ResponseEntity.ok(moedaDTO);
        } catch (MoedaNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Buscar moeda por código")
    public ResponseEntity<MoedaDTO> buscarPorCodigo(@PathVariable String codigo) {
        MoedaDTO moedaDTO = moedaService.buscarPorCodigo(codigo);
        return moedaDTO != null ? ResponseEntity.ok(moedaDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Listar todas as moedas")
    public ResponseEntity<List<MoedaDTO>> listarTodas() {
        return ResponseEntity.ok(moedaService.listarTodas());
    }
} 