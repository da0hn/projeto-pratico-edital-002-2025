package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiResponse;
import br.com.ghonda.core.dto.AuthenticationPayload;
import br.com.ghonda.core.dto.AuthenticationDetailPayload;
import br.com.ghonda.core.dto.RefreshTokenPayload;
import br.com.ghonda.core.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para autenticação")
public class AuthenticationResource {

    private final AuthenticationService authenticationService;

    @PostMapping("/token")
    @Operation(
        summary = "Autenticar usuário",
        description = "Endpoint para autenticar um usuário e obter um token de acesso",
        tags = "Autenticação"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Usuário autenticado com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "401",
                description = "Credenciais inválidas"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiResponse<AuthenticationDetailPayload>> authenticate(
        @RequestBody final AuthenticationPayload request
    ) {
        final var response = this.authenticationService.authenticate(request);
        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @PostMapping("/refresh-token")
    @Operation(
        summary = "Atualizar token",
        description = "Endpoint para atualizar o token de acesso",
        tags = "Autenticação"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Token atualizado com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "401",
                description = "Token inválido"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiResponse<AuthenticationDetailPayload>> refreshToken(
        @RequestBody final RefreshTokenPayload request
    ) {
        final var response = this.authenticationService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.of(response));
    }

}
