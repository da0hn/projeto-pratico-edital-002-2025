package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiResponse;
import br.com.ghonda.core.dto.NewUnidadePayload;
import br.com.ghonda.core.dto.UnidadeDetailPayload;
import br.com.ghonda.core.dto.UpdateUnidadePayload;
import br.com.ghonda.core.service.UnidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/unidades")
@Tag(name = "Unidade", description = "Endpoints para gestão de unidades")
public class UnidadeResource {

    private final UnidadeService unidadeService;

    @PostMapping
    @Operation(
        summary = "Registrar unidade",
        description = "Endpoint para registrar uma nova unidade"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Unidade registrada com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Dados inválidos fornecidos"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiResponse<UnidadeDetailPayload>> registrar(
        @Valid @RequestBody final NewUnidadePayload payload
    ) {
        log.info("Registrar unidade: {}", payload);

        final var response = this.unidadeService.registrar(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.of(response));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar unidade por ID",
        description = "Endpoint para buscar uma unidade pelo seu ID"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Unidade encontrada com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Unidade não encontrada"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiResponse<UnidadeDetailPayload>> findById(@PathVariable final Long id) {
        log.info("Buscar unidade por id: {}", id);

        final var response = this.unidadeService.findById(id);

        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar unidade",
        description = "Endpoint para atualizar uma unidade existente"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Unidade atualizada com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Dados inválidos fornecidos"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Unidade não encontrada"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiResponse<UnidadeDetailPayload>> update(
        @PathVariable final Long id,
        @Valid @RequestBody final UpdateUnidadePayload payload
    ) {
        log.info("Atualizar unidade: {}", payload);

        final var response = this.unidadeService.update(payload);

        return ResponseEntity.ok(ApiResponse.of(response));
    }

}
