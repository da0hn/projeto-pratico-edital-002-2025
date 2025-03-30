package br.com.ghonda.core.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record UpdateServidorEfetivoPayload(
    @NotNull(message = "Id é obrigatório")
    Long id,
    @Valid
    @JsonUnwrapped
    PessoaDetailPayload pessoa,
    @NotBlank(message = "Matricula é obrigatória")
    String matricula
) {
}
