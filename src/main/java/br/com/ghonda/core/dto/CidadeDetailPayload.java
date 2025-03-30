package br.com.ghonda.core.dto;

import br.com.ghonda.core.annotations.Enumerator;
import br.com.ghonda.core.domain.UF;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CidadeDetailPayload(
    @NotBlank(message = "Nome é obrigatório")
    String nome,
    @NotNull(message = "UF é obrigatória")
    @Enumerator(enumClass = UF.class, message = "UF informada não é válida")
    UF uf
) {

}
