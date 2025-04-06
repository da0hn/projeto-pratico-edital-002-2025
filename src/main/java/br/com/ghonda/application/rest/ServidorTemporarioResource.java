package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiCollectionPageResponse;
import br.com.ghonda.application.rest.payload.ApiResponse;
import br.com.ghonda.core.domain.UF;
import br.com.ghonda.core.dto.NewServidorTemporarioPayload;
import br.com.ghonda.core.dto.SearchServidorEfetivoPayload;
import br.com.ghonda.core.dto.SearchServidorTemporarioPayload;
import br.com.ghonda.core.dto.ServidorSimpleDetailPayload;
import br.com.ghonda.core.dto.UpdateServidorTemporarioPayload;
import br.com.ghonda.core.service.ServidorTemporarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/v1/servidores/temporarios")
public class ServidorTemporarioResource {

    private final ServidorTemporarioService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ServidorSimpleDetailPayload>> registrarServidorEfetivo(@Valid @RequestBody final NewServidorTemporarioPayload payload) {
        log.info("Registrar servidor temporário: {}", payload);

        final var response = this.service.registrar(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.of(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServidorSimpleDetailPayload>> findById(@PathVariable final Long id) {
        log.info("Buscar servidor temporário por id: {}", id);

        final var response = this.service.findById(id);

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
        log.info("Buscar servidores temporário com nome: {}, matricula: {}, nomeCidade: {}, uf: {}, pageable: {}", nome, matricula, nomeCidade, uf, pageable);

        final var response = this.service.findAll(
            SearchServidorTemporarioPayload.builder()
                .nome(nome)
                .nomeCidade(nomeCidade)
                .uf(uf)
                .pageable(pageable)
                .build()
        );

        return ResponseEntity.ok(ApiCollectionPageResponse.of(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ServidorSimpleDetailPayload>> update(
        @PathVariable final Long id,
        @Valid @RequestBody final UpdateServidorTemporarioPayload payload
    ) {
        log.info("Atualizar servidor temporário: {}", payload);

        final var response = this.service.update(payload);

        return ResponseEntity.ok(ApiResponse.of(response));
    }

}
