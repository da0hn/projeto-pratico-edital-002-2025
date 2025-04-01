package br.com.ghonda.core.dto;

import br.com.ghonda.core.domain.Usuario;
import br.com.ghonda.core.enums.Role;
import lombok.Builder;

@Builder
public record UsuarioDetailPayload(Long id, String nome, String email, Role role) {

    public static UsuarioDetailPayload of(final Usuario usuario) {
        return UsuarioDetailPayload.builder()
            .id(usuario.getId())
            .nome(usuario.getNome())
            .email(usuario.getEmail())
            .role(usuario.getRole())
            .build();
    }

}
