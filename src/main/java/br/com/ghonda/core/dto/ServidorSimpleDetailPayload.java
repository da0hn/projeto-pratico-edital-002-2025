package br.com.ghonda.core.dto;

import br.com.ghonda.core.domain.ServidorEfetivo;
import br.com.ghonda.core.domain.ServidorTemporario;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Builder
@Jacksonized
public record ServidorSimpleDetailPayload(
    Long id,
    @JsonUnwrapped
    PessoaDetailPayload pessoa,
    String matricula,
    LocalDate dataAdmissao,
    LocalDate dataDemissao
) {

    public static ServidorSimpleDetailPayload of(final ServidorEfetivo efetivo) {
        return new ServidorSimpleDetailPayload(
            efetivo.getId(),
            PessoaDetailPayload.of(efetivo),
            efetivo.getMatricula(),
            null,
            null
        );
    }

    public static ServidorSimpleDetailPayload of(final ServidorTemporario temporario) {
        return new ServidorSimpleDetailPayload(
            temporario.getId(),
            PessoaDetailPayload.of(temporario),
            null,
            temporario.getDataAdmissao(),
            temporario.getDataDemissao()
        );
    }

}
