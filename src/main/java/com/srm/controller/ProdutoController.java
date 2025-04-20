package com.srm.controller;

import com.srm.dto.ProdutoDTO;
import com.srm.service.ProdutoService;
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

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "API para gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Operation(summary = "Criar um novo produto", description = "Cria um novo produto com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto criado com sucesso",
            content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<ProdutoDTO> criarProduto(@RequestBody ProdutoDTO produtoDTO) {
        log.info("Recebendo requisição para criar produto: {}", produtoDTO);
        try {
            ProdutoDTO produtoCriado = produtoService.criar(produtoDTO);
            log.info("Produto criado com sucesso: {}", produtoCriado);
            return ResponseEntity.created(URI.create("/api/produtos/" + produtoCriado.getId())).body(produtoCriado);
        } catch (Exception e) {
            log.error("Erro ao criar produto", e);
            throw e;
        }
    }

    @Operation(summary = "Atualizar um produto", description = "Atualiza os dados de um produto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(
            @Parameter(description = "ID do produto", example = "1")
            @PathVariable Long id,
            @RequestBody ProdutoDTO produtoDTO) {
        log.info("Recebendo requisição para atualizar produto com id {}: {}", id, produtoDTO);
        try {
            ProdutoDTO produtoAtualizado = produtoService.atualizar(id, produtoDTO);
            log.info("Produto atualizado com sucesso: {}", produtoAtualizado);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar produto", e);
            throw e;
        }
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna os dados de um produto específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado",
            content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(
            @Parameter(description = "ID do produto", example = "1")
            @PathVariable Long id) {
        log.info("Recebendo requisição para buscar produto com id: {}", id);
        try {
            return produtoService.buscarPorId(id)
                    .map(produto -> {
                        log.info("Produto encontrado: {}", produto);
                        return ResponseEntity.ok(produto);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Erro ao buscar produto", e);
            throw e;
        }
    }

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso",
            content = @Content(schema = @Schema(implementation = ProdutoDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodos() {
        log.info("Recebendo requisição para listar todos os produtos");
        try {
            List<ProdutoDTO> produtos = produtoService.listarTodos();
            log.info("Total de produtos encontrados: {}", produtos.size());
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            log.error("Erro ao listar produtos", e);
            throw e;
        }
    }

    @Operation(summary = "Deletar um produto", description = "Remove um produto do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(
            @Parameter(description = "ID do produto", example = "1")
            @PathVariable Long id) {
        log.info("Recebendo requisição para deletar produto com id: {}", id);
        try {
            produtoService.deletar(id);
            log.info("Produto deletado com sucesso");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao deletar produto", e);
            throw e;
        }
    }
} 