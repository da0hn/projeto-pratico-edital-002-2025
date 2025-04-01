package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiCollectionPageResponse;
import br.com.ghonda.application.rest.payload.ApiResponse;
import br.com.ghonda.core.dto.NewUsuarioPayload;
import br.com.ghonda.core.dto.UpdateUsuarioPayload;
import br.com.ghonda.core.dto.UsuarioDetailPayload;
import br.com.ghonda.core.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioDetailPayload>> criar(@Valid @RequestBody final NewUsuarioPayload payload) {
        final var response = this.usuarioService.registrar(payload);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.of(response));
    }

    @GetMapping
    public ResponseEntity<ApiCollectionPageResponse<UsuarioDetailPayload>> listar(final Pageable pageable) {
        final var usuarios = this.usuarioService.findAll(pageable);
        return ResponseEntity.ok(ApiCollectionPageResponse.of(usuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDetailPayload>> findById(@PathVariable final Long id) {
        final var response = this.usuarioService.findById(id);
        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDetailPayload>> atualizar(
        @PathVariable final Long id,
        @Valid @RequestBody final UpdateUsuarioPayload payload
    ) {
        final var response = this.usuarioService.update(id, payload);
        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> excluir(@PathVariable final Long id) {
        this.usuarioService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.empty());
    }

}
