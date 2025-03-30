package br.com.ghonda.core.dto;

import br.com.ghonda.core.annotations.Period;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Period(
    beginDate = "dataAdmissao",
    endDate = "dataDemissao",
    message = "A data de demissão deve ser maior que a data de admissão"
)
public record ServidorTemporarioPayload(
    @JsonUnwrapped
    PessoaDetailPayload pessoa,
    @NotNull(message = "Data de admissão é obrigatória")
    LocalDate dataAdmissao,
    @NotNull(message = "Data de demissão é obrigatória")
    LocalDate dataDemissao
) {
}
