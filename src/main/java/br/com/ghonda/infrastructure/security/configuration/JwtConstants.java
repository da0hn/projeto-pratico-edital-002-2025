package br.com.ghonda.infrastructure.security.configuration;

public final class JwtConstants {

    private JwtConstants() { }

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();

}
