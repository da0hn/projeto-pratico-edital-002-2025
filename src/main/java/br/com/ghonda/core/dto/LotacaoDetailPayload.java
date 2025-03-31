package br.com.ghonda.core.dto;

import br.com.ghonda.core.domain.Lotacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Builder
@Jacksonized
public record LotacaoDetailPayload(
    Long id,
    Long idServidor,
    Long idUnidade,
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataLotacao,
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataRemocao,
    String portaria
) {

    public static LotacaoDetailPayload of(final Lotacao newLotacao) {
        return LotacaoDetailPayload.builder()
            .id(newLotacao.getId())
            .idServidor(newLotacao.getPessoa().getId())
            .idUnidade(newLotacao.getUnidade().getId())
            .dataLotacao(newLotacao.getDataLotacao())
            .dataRemocao(newLotacao.getDataRemocao())
            .portaria(newLotacao.getPortaria())
            .build();
    }

}
