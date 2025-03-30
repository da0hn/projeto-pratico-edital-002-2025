package br.com.ghonda.core.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record NewServidorEfetivoPayload(
    @JsonUnwrapped
    @Valid
    PessoaDetailPayload pessoa,
    @NotBlank(message = "Matricula é obrigatória")
    String matricula
) {
}
