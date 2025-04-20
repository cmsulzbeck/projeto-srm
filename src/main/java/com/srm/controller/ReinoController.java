package com.srm.controller;

import com.srm.dto.ReinoDTO;
import com.srm.service.ReinoService;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reinos")
@RequiredArgsConstructor
@Tag(name = "Reinos", description = "API para gerenciamento de reinos")
public class ReinoController {

    private final ReinoService reinoService;

    @Operation(summary = "Criar um novo reino", description = "Cria um novo reino com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reino criado com sucesso",
            content = @Content(schema = @Schema(implementation = ReinoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<ReinoDTO> criarReino(@RequestBody ReinoDTO reinoDTO) {
        log.info("Recebendo requisição para criar reino: {}", reinoDTO);
        try {
            ReinoDTO reinoCriado = reinoService.criarReino(reinoDTO);
            log.info("Reino criado com sucesso: {}", reinoCriado);
            return ResponseEntity.ok(reinoCriado);
        } catch (Exception e) {
            log.error("Erro ao criar reino", e);
            throw e;
        }
    }

    @Operation(summary = "Buscar reino por ID", description = "Retorna os dados de um reino específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reino encontrado",
            content = @Content(schema = @Schema(implementation = ReinoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Reino não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReinoDTO> buscarPorId(
            @Parameter(description = "ID do reino", example = "1")
            @PathVariable Long id) {
        log.info("Recebendo requisição para buscar reino com id: {}", id);
        try {
            ReinoDTO reino = reinoService.buscarPorId(id);
            log.info("Reino encontrado: {}", reino);
            return ResponseEntity.ok(reino);
        } catch (Exception e) {
            log.error("Erro ao buscar reino", e);
            throw e;
        }
    }

    @Operation(summary = "Listar todos os reinos", description = "Retorna uma lista com todos os reinos cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de reinos retornada com sucesso",
            content = @Content(schema = @Schema(implementation = ReinoDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<ReinoDTO>> listarTodos() {
        log.info("Recebendo requisição para listar todos os reinos");
        try {
            List<ReinoDTO> reinos = reinoService.listarTodos();
            log.info("Total de reinos encontrados: {}", reinos.size());
            return ResponseEntity.ok(reinos);
        } catch (Exception e) {
            log.error("Erro ao listar reinos", e);
            throw e;
        }
    }

    @Operation(summary = "Atualizar um reino", description = "Atualiza os dados de um reino existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reino atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = ReinoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Reino não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReinoDTO> atualizarReino(
            @Parameter(description = "ID do reino", example = "1")
            @PathVariable Long id,
            @RequestBody ReinoDTO reinoDTO) {
        log.info("Recebendo requisição para atualizar reino com id {}: {}", id, reinoDTO);
        try {
            ReinoDTO reinoAtualizado = reinoService.atualizarReino(id, reinoDTO);
            log.info("Reino atualizado com sucesso: {}", reinoAtualizado);
            return ResponseEntity.ok(reinoAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar reino", e);
            throw e;
        }
    }

    @Operation(summary = "Deletar um reino", description = "Remove um reino do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reino deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Reino não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReino(
            @Parameter(description = "ID do reino", example = "1")
            @PathVariable Long id) {
        log.info("Recebendo requisição para deletar reino com id: {}", id);
        try {
            reinoService.deletarReino(id);
            log.info("Reino deletado com sucesso");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao deletar reino", e);
            throw e;
        }
    }
} 