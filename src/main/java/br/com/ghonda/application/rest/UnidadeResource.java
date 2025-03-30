package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiResponse;
import br.com.ghonda.core.dto.NewUnidadePayload;
import br.com.ghonda.core.dto.UnidadeDetailPayload;
import br.com.ghonda.core.dto.UpdateUnidadePayload;
import br.com.ghonda.core.service.UnidadeService;
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
public class UnidadeResource {

    private final UnidadeService unidadeService;

    @PostMapping
    public ResponseEntity<ApiResponse<UnidadeDetailPayload>> registrar(
        @Valid @RequestBody final NewUnidadePayload payload
    ) {
        log.info("Registrar unidade: {}", payload);

        final var response = this.unidadeService.registrar(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.of(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UnidadeDetailPayload>> findById(@PathVariable final Long id) {
        log.info("Buscar unidade por id: {}", id);

        final var response = this.unidadeService.findById(id);

        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UnidadeDetailPayload>> update(
        @PathVariable final Long id,
        @Valid @RequestBody final UpdateUnidadePayload payload
    ) {
        log.info("Atualizar unidade: {}", payload);

        final var response = this.unidadeService.update(payload);

        return ResponseEntity.ok(ApiResponse.of(response));
    }

}
