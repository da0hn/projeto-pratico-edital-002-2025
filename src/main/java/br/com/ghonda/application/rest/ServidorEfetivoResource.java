package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiCollectionPageResponse;
import br.com.ghonda.application.rest.payload.ApiResponse;
import br.com.ghonda.core.domain.UF;
import br.com.ghonda.core.dto.EnderecoFuncionalDetailPayload;
import br.com.ghonda.core.dto.NewServidorEfetivoPayload;
import br.com.ghonda.core.dto.SearchServidorEfetivoPayload;
import br.com.ghonda.core.dto.ServidorEfetivoLotadoPayload;
import br.com.ghonda.core.dto.ServidorSimpleDetailPayload;
import br.com.ghonda.core.dto.UpdateServidorEfetivoPayload;
import br.com.ghonda.core.service.ServidorEfetivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

@Slf4j
@RestController
@RequestMapping("/v1/servidores/efetivos")
@Tag(name = "Servidor Efetivo", description = "Endpoints para gestão de servidores efetivos")
public class ServidorEfetivoResource {

    private final ServidorEfetivoService service;

    public ServidorEfetivoResource(final ServidorEfetivoService service) { this.service = service; }

    @PostMapping
    @Operation(
        summary = "Registrar servidor efetivo",
        description = "Endpoint para registrar um novo servidor efetivo"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Servidor efetivo registrado com sucesso"
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
    public ResponseEntity<ApiResponse<ServidorSimpleDetailPayload>> registrarServidorEfetivo(@Valid @RequestBody final NewServidorEfetivoPayload payload) {
        log.info("Registrar servidor efetivo: {}", payload);

        final var response = this.service.registrar(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.of(response));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar servidor efetivo por ID",
        description = "Endpoint para buscar um servidor efetivo pelo ID"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Servidor efetivo encontrado"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Servidor efetivo não encontrado"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiResponse<ServidorSimpleDetailPayload>> findById(@PathVariable final Long id) {
        log.info("Buscar servidor efetivo por id: {}", id);

        final var response = this.service.findById(id);

        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar servidor efetivo",
        description = "Endpoint para atualizar um servidor efetivo existente"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Servidor efetivo atualizado com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Dados inválidos fornecidos"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Servidor efetivo não encontrado"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiResponse<ServidorSimpleDetailPayload>> update(
        @PathVariable final Long id,
        @Valid @RequestBody final UpdateServidorEfetivoPayload payload
    ) {
        log.info("Atualizar servidor efetivo: {}", payload);

        final var response = this.service.update(payload);

        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @GetMapping
    @Operation(
        summary = "Buscar servidores efetivos",
        description = "Endpoint para buscar servidores efetivos com paginação"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Servidores efetivos encontrados com sucesso"
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
    public ResponseEntity<ApiCollectionPageResponse<ServidorSimpleDetailPayload>> findAll(
        @RequestParam(value = "nome", required = false) final String nome,
        @RequestParam(value = "matricula", required = false) final String matricula,
        @RequestParam(value = "nome-cidade", required = false) final String nomeCidade,
        @RequestParam(value = "uf", required = false) final UF uf,
        @PageableDefault(size = 15, sort = { "nome" }, direction = Sort.Direction.ASC) final Pageable pageable
    ) {
        log.info(
            "Buscar servidores efetivos com nome: {}, matricula: {}, nomeCidade: {}, uf: {}, pageable: {}",
            nome,
            matricula,
            nomeCidade,
            uf,
            pageable
        );

        final var response = this.service.findAll(
            SearchServidorEfetivoPayload.builder()
                .nome(nome)
                .matricula(matricula)
                .nomeCidade(nomeCidade)
                .uf(uf)
                .pageable(pageable)
                .build()
        );

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ApiCollectionPageResponse.of(response));
    }

    @GetMapping("/lotados")
    @Operation(
        summary = "Buscar servidores efetivos lotados",
        description = "Endpoint para buscar servidores efetivos lotados em uma unidade com paginação"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Servidores efetivos lotados encontrados com sucesso"
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
    public ResponseEntity<ApiCollectionPageResponse<ServidorEfetivoLotadoPayload>> findAllLotados(
        @RequestParam("unidade-id") final Long unidadeId,
        @PageableDefault(sort = "nome", direction = Sort.Direction.ASC) final Pageable pageable
    ) {
        log.info("Buscar servidores efetivos lotados na unidade: {}, pageable: {}", unidadeId, pageable);
        final var response = this.service.findAllLotados(unidadeId, pageable);

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ApiCollectionPageResponse.of(response));
    }

    @GetMapping("/endereco-funcional")
    @Operation(
        summary = "Buscar endereços funcionais",
        description = "Endpoint para buscar endereços funcionais com paginação"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Endereços funcionais encontrados com sucesso"
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
    public ResponseEntity<ApiCollectionPageResponse<EnderecoFuncionalDetailPayload>> findEnderecoFuncional(
        @RequestParam("nome") final String nome,
        final Pageable pageable
    ) {
        log.info("Buscar endereços funcionais com nome: {}, pageable: {}", nome, pageable);

        final var response = this.service.findEnderecoFuncional(nome, pageable);

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ApiCollectionPageResponse.of(response));
    }

}
