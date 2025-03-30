package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiResponse;
import br.com.ghonda.core.dto.NewServidorTemporarioPayload;
import br.com.ghonda.core.dto.ServidorSimpleDetailPayload;
import br.com.ghonda.core.dto.UpdateServidorTemporarioPayload;
import br.com.ghonda.core.service.ServidorTemporarioService;
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
