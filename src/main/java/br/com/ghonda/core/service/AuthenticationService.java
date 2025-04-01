package br.com.ghonda.core.service;

import br.com.ghonda.core.dto.AuthenticationPayload;
import br.com.ghonda.core.dto.AuthenticationDetailPayload;
import br.com.ghonda.core.dto.RefreshTokenPayload;
import br.com.ghonda.core.exceptions.InvalidTokenException;
import br.com.ghonda.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    public AuthenticationDetailPayload authenticate(final AuthenticationPayload payload) {
        this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                payload.getEmail(),
                payload.getPassword()
            )
        );

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(payload.getEmail());
        final String token = this.jwtService.generateToken(userDetails);
        final String refreshToken = this.jwtService.generateRefreshToken(userDetails);

        return AuthenticationDetailPayload.builder()
            .token(token)
            .refreshToken(refreshToken)
            .build();
    }

    public AuthenticationDetailPayload refreshToken(final RefreshTokenPayload payload) {
        final String username = this.jwtService.extractUsername(payload.getRefreshToken());
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        if (!this.jwtService.isTokenValid(payload.getRefreshToken(), userDetails)) {
            throw new InvalidTokenException("Token de atualização inválido");
        }
        final String token = this.jwtService.generateToken(userDetails);
        final String refreshToken = this.jwtService.generateRefreshToken(userDetails);

        return AuthenticationDetailPayload.builder()
            .token(token)
            .refreshToken(refreshToken)
            .build();

    }

}
