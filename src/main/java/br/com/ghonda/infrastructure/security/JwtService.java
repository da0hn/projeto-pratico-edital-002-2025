package br.com.ghonda.infrastructure.security;

import br.com.ghonda.infrastructure.security.configuration.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;

    public String extractUsername(final String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(final UserDetails userDetails) {
        return this.generateToken(new HashMap<>(), userDetails);
    }

    public String generateRefreshToken(final UserDetails userDetails) {
        return Jwts
            .builder()
            .setClaims(new HashMap<>())
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + this.jwtConfig.getRefreshExpiration()))
            .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateToken(final Map<String, Object> extraClaims, final UserDetails userDetails) {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + this.jwtConfig.getExpiration()))
            .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isTokenValid(final String token, final UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !this.isTokenExpired(token);
    }

    private boolean isTokenExpired(final String token) {
        return this.extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(this.getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(this.jwtConfig.getSecret().getBytes());
    }

}
