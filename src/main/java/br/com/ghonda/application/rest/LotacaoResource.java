package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiCollectionPageResponse;
import br.com.ghonda.application.rest.payload.ApiResponse;
import br.com.ghonda.core.dto.LotacaoDetailPayload;
import br.com.ghonda.core.dto.NewLotacaoPayload;
import br.com.ghonda.core.dto.SearchLotacaoPayload;
import br.com.ghonda.core.dto.UpdateLotacaoPayload;
import br.com.ghonda.core.service.LotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/lotacoes")
@Tag(name = "Lotação", description = "Endpoints para gestão de lotações")
public class LotacaoResource {

    private final LotacaoService lotacaoService;

    @PostMapping
    @Operation(
        summary = "Registrar lotação",
        description = "Endpoint para registrar uma nova lotação"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Lotação registrada com sucesso"
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
    public ResponseEntity<ApiResponse<LotacaoDetailPayload>> registrarLotacao(@Valid @RequestBody final NewLotacaoPayload payload) {
        log.info("Registrar lotação: {}", payload);

        final var response = this.lotacaoService.registrar(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.of(response));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar lotação",
        description = "Endpoint para atualizar uma lotação existente"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Lotação atualizada com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Dados inválidos fornecidos"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Lotação não encontrada"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiResponse<LotacaoDetailPayload>> atualizarLotacao(@Valid @RequestBody final UpdateLotacaoPayload payload) {
        log.info("Atualizar lotação: {}", payload);

        final var response = this.lotacaoService.update(payload);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.of(response));
    }

    @GetMapping
    @Operation(
        summary = "Buscar lotações",
        description = "Endpoint para buscar lotações com paginação"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Lotações encontradas com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "204",
                description = "Nenhum dado encontrado"
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
    public ResponseEntity<ApiCollectionPageResponse<LotacaoDetailPayload>> findAll(
        @RequestParam(value = "id", required = false) final Long id,
        @RequestParam(value = "servidorId", required = false) final Long servidorId,
        @RequestParam(value = "unidadeId", required = false) final Long unidadeId,
        @RequestParam(value = "portaria", required = false) final String portaria,
        @RequestParam(value = "data-inicio-lotacao", required = false) final LocalDate dataInicioLotacao,
        @RequestParam(value = "data-fim-lotacao", required = false) final LocalDate dataFimLotacao,
        @RequestParam(value = "data-inicio-remocao", required = false) final LocalDate dataInicioRemocao,
        @RequestParam(value = "data-fim-remocao", required = false) final LocalDate dataFimRemocao,
        final Pageable pageable
    ) {
        log.info("Buscar lotações com paginação: {}", pageable);

        final var response = this.lotacaoService.findAll(
            SearchLotacaoPayload.builder()
                .id(id)
                .servidorId(servidorId)
                .unidadeId(unidadeId)
                .portaria(portaria)
                .dataInicioLotacao(dataInicioLotacao)
                .dataFimLotacao(dataFimLotacao)
                .dataInicioRemocao(dataInicioRemocao)
                .dataFimRemocao(dataFimRemocao)
                .build()
        );
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiCollectionPageResponse.of(response));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar lotação por ID",
        description = "Endpoint para buscar uma lotação específica pelo ID"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Lotação encontrada com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Lotação não encontrada"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiResponse<LotacaoDetailPayload>> findById(@PathVariable final Long id) {
        log.info("Buscar lotação por id: {}", id);

        final var response = this.lotacaoService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.of(response));
    }

}
