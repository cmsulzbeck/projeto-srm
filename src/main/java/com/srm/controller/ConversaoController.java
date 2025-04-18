package com.srm.controller;

import com.srm.dto.ConversaoDTO;
import com.srm.service.ConversaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/conversoes")
@Tag(name = "Conversões", description = "API para conversão de moedas")
public class ConversaoController {

    @Autowired
    private ConversaoService conversaoService;

    @PostMapping
    @Operation(summary = "Realizar conversão de moeda")
    public ResponseEntity<BigDecimal> converterMoeda(@RequestBody ConversaoDTO conversaoDTO) {
        try {
            BigDecimal resultado = conversaoService.converter(
                conversaoDTO.getMoedaOrigem(),
                conversaoDTO.getMoedaDestino(),
                conversaoDTO.getValorOriginal()
            );
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 