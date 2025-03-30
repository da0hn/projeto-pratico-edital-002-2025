package br.com.ghonda.core.dto;

import br.com.ghonda.core.annotations.Enumerator;
import br.com.ghonda.core.domain.Cidade;
import br.com.ghonda.core.domain.UF;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CidadeDetailPayload(
    @NotBlank(message = "Nome é obrigatório")
    String nome,
    @NotNull(message = "UF é obrigatória")
    @Enumerator(enumClass = UF.class, message = "UF informada não é válida")
    UF uf
) {

    public static CidadeDetailPayload of(final Cidade cidade) {
        return new CidadeDetailPayload(
            cidade.getNome(),
            cidade.getUf()
        );
    }

}
