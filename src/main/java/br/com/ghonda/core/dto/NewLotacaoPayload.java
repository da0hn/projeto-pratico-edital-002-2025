package br.com.ghonda.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewLotacaoPayload(
    @NotNull(message = "O id do servidor não pode ser nulo")
    Long servidorId,
    @NotNull(message = "O id da unidade não pode ser nulo")
    Long unidadeId,
    @NotNull(message = "A data de lotação não pode ser nula")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataLotacao,
    @NotNull(message = "A data de remoção não pode ser nula")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataRemocao,
    @Size(max = 100, message = "A portaria deve ter no máximo 100 caracteres")
    @NotBlank(message = "A portaria não pode ser nula")
    String portaria
) {
}
