package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiCollectionPageResponse;
import br.com.ghonda.application.rest.payload.ApiResponse;
import br.com.ghonda.core.domain.UF;
import br.com.ghonda.core.dto.EnderecoDetailPayload;
import br.com.ghonda.core.dto.EnderecoFuncionalDetailPayload;
import br.com.ghonda.core.dto.NewServidorEfetivoPayload;
import br.com.ghonda.core.dto.SearchServidorEfetivoPayload;
import br.com.ghonda.core.dto.ServidorEfetivoLotadoPayload;
import br.com.ghonda.core.dto.ServidorSimpleDetailPayload;
import br.com.ghonda.core.dto.UpdateServidorEfetivoPayload;
import br.com.ghonda.core.service.ServidorEfetivoService;
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
public class ServidorEfetivoResource {

    private final ServidorEfetivoService service;

    public ServidorEfetivoResource(final ServidorEfetivoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ApiResponse<ServidorSimpleDetailPayload>> registrarServidorEfetivo(@Valid @RequestBody final NewServidorEfetivoPayload payload) {
        log.info("Registrar servidor efetivo: {}", payload);

        final var response = this.service.registrar(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.of(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServidorSimpleDetailPayload>> findById(@PathVariable final Long id) {
        log.info("Buscar servidor efetivo por id: {}", id);

        final var response = this.service.findById(id);

        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ServidorSimpleDetailPayload>> update(
        @PathVariable final Long id,
        @Valid @RequestBody final UpdateServidorEfetivoPayload payload
    ) {
        log.info("Atualizar servidor efetivo: {}", payload);

        final var response = this.service.update(payload);

        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @GetMapping
    public ResponseEntity<ApiCollectionPageResponse<ServidorSimpleDetailPayload>> findAll(
        @RequestParam(value = "nome", required = false) final String nome,
        @RequestParam(value = "matricula", required = false) final String matricula,
        @RequestParam(value = "nome-cidade", required = false) final String nomeCidade,
        @RequestParam(value = "uf", required = false) final UF uf,
        @PageableDefault(size = 15, sort = { "nome" }, direction = Sort.Direction.ASC) final Pageable pageable
    ) {
        log.info("Buscar servidores efetivos com nome: {}, matricula: {}, nomeCidade: {}, uf: {}, pageable: {}", nome, matricula, nomeCidade, uf, pageable);

        final var response = this.service.findAll(
            SearchServidorEfetivoPayload.builder()
                .nome(nome)
                .matricula(matricula)
                .nomeCidade(nomeCidade)
                .uf(uf)
                .pageable(pageable)
                .build()
        );

        return ResponseEntity.ok(ApiCollectionPageResponse.of(response));
    }

    @GetMapping("/lotados")
    public ResponseEntity<ApiCollectionPageResponse<ServidorEfetivoLotadoPayload>> findAllLotados(
        @RequestParam("unidade-id") final Long unidadeId,
        @PageableDefault(sort = "nome", direction = Sort.Direction.ASC) final Pageable pageable
    ) {
        log.info("Buscar servidores efetivos lotados na unidade: {}, pageable: {}", unidadeId, pageable);
        return ResponseEntity.ok(ApiCollectionPageResponse.of(this.service.findAllLotados(unidadeId, pageable)));
    }

    @GetMapping("/endereco-funcional")
    public ResponseEntity<ApiCollectionPageResponse<EnderecoFuncionalDetailPayload>> findEnderecoFuncional(
        @RequestParam("nome") final String nome,
        final Pageable pageable
    ) {
        log.info("Buscar endereços funcionais com nome: {}, pageable: {}", nome, pageable);
        return ResponseEntity.ok(ApiCollectionPageResponse.of(this.service.findEnderecoFuncional(nome,pageable)));
    }

}
