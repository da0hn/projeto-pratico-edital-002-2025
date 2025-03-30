package br.com.ghonda.core.dto;

import br.com.ghonda.core.domain.ServidorEfetivo;
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

    public static ServidorSimpleDetailPayload of(final ServidorEfetivo servidor) {
        return new ServidorSimpleDetailPayload(
            servidor.getId(),
            PessoaDetailPayload.of(servidor),
            servidor.getMatricula(),
            null,
            null
        );
    }

}
