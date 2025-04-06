package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiCollectionPageResponse;
import br.com.ghonda.application.rest.payload.ApiResponse;
import br.com.ghonda.core.dto.NewUsuarioPayload;
import br.com.ghonda.core.dto.UpdateUsuarioPayload;
import br.com.ghonda.core.dto.UsuarioDetailPayload;
import br.com.ghonda.core.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/usuarios")
@AllArgsConstructor
@Tag(name = "Usuário", description = "Endpoints para gestão de usuários")
public class UsuarioResource {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(
        summary = "Registrar usuário",
        description = "Endpoint para registrar um novo usuário"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Usuário registrado com sucesso"
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
    public ResponseEntity<ApiResponse<UsuarioDetailPayload>> criar(@Valid @RequestBody final NewUsuarioPayload payload) {
        log.info("Registrar usuário nome: {}, email: {}, role: {}", payload.nome(), payload.email(), payload.role());

        final var response = this.usuarioService.registrar(payload);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.of(response));
    }

    @GetMapping
    @Operation(
        summary = "Listar usuários",
        description = "Endpoint para listar todos os usuários"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Usuários listados com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "204",
                description = "Nenhum usuário encontrado"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiCollectionPageResponse<UsuarioDetailPayload>> listar(final Pageable pageable) {
        log.info("Listar usuários: {}", pageable);

        final var usuarios = this.usuarioService.findAll(pageable);

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ApiCollectionPageResponse.of(usuarios));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar usuário por ID",
        description = "Endpoint para buscar um usuário pelo ID"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Usuário encontrado com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Usuário não encontrado"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiResponse<UsuarioDetailPayload>> findById(@PathVariable final Long id) {
        log.info("Buscar usuário por id: {}", id);

        final var response = this.usuarioService.findById(id);
        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar usuário",
        description = "Endpoint para atualizar um usuário existente"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Usuário atualizado com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Dados inválidos fornecidos"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Usuário não encontrado"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiResponse<UsuarioDetailPayload>> atualizar(
        @PathVariable final Long id,
        @Valid @RequestBody final UpdateUsuarioPayload payload
    ) {
        log.info("Atualizar usuário: {}", payload);

        final var response = this.usuarioService.update(id, payload);
        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir usuário",
        description = "Endpoint para excluir um usuário pelo ID"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Usuário excluído com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Usuário não encontrado"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiResponse<Void>> excluir(@PathVariable final Long id) {
        log.info("Excluir usuário por id: {}", id);

        this.usuarioService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.empty());
    }

}
