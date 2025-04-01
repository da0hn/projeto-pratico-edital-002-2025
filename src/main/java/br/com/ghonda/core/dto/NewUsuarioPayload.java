package br.com.ghonda.core.dto;

import br.com.ghonda.core.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record NewUsuarioPayload(
    @NotBlank(message = "O nome é obrigatório")
    String nome,
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail deve ser válido")
    String email,
    @NotBlank(message = "A senha é obrigatória")
    String password,
    @NotNull(message = "O papel do usuário é obrigatório")
    Role role
) {

}
