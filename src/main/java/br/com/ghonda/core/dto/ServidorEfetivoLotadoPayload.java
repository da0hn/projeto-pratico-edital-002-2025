package br.com.ghonda.core.dto;

import br.com.ghonda.core.repository.projections.ServidorEfetivoLotadoProjection;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Builder
@Jacksonized
public record ServidorEfetivoLotadoPayload(
    String nome,
    Integer idade,
    Long unidadeId,
    String unidadeNome,
    String unidadeSigla,
    List<String> fotografias
) {

    public static ServidorEfetivoLotadoPayload of(
        final ServidorEfetivoLotadoProjection projection,
        final List<String> urls
    ) {
        return ServidorEfetivoLotadoPayload.builder()
            .nome(projection.getNome())
            .idade(Period.between(projection.getDataNascimento(), LocalDate.now()).getYears())
            .unidadeId(projection.getUnidadeId())
            .unidadeNome(projection.getUnidadeNome())
            .unidadeSigla(projection.getUnidadeSigla())
            .fotografias(urls)
            .build();
    }

}
