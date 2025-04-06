package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiResponse;
import br.com.ghonda.core.dto.AuthenticationPayload;
import br.com.ghonda.core.dto.AuthenticationDetailPayload;
import br.com.ghonda.core.dto.RefreshTokenPayload;
import br.com.ghonda.core.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationResource {

    private final AuthenticationService authenticationService;

    @PostMapping("/token")
    public ResponseEntity<ApiResponse<AuthenticationDetailPayload>> authenticate(
        @RequestBody final AuthenticationPayload request
    ) {
        final var response = this.authenticationService.authenticate(request);
        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthenticationDetailPayload>> refreshToken(
        @RequestBody final RefreshTokenPayload request
    ) {
        final var response = this.authenticationService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.of(response));
    }

}
