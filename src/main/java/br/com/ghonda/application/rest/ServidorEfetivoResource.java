package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiResponse;
import br.com.ghonda.core.dto.ServidorEfetivoPayload;
import br.com.ghonda.core.service.ServidorEfetivoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/servidores/efetivos")
public class ServidorEfetivoResource {

    private final ServidorEfetivoService service;

    public ServidorEfetivoResource(final ServidorEfetivoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> registrarServidorEfetivo(@Valid @RequestBody final ServidorEfetivoPayload payload) {
        log.info("Registrar servidor efetivo: {}", payload);

        this.service.registrar(payload);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(null);
    }

}
