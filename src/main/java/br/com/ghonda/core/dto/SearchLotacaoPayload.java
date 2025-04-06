package br.com.ghonda.core.dto;

import lombok.Builder;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Builder
public record SearchLotacaoPayload(
    Long id,
    Long servidorId,
    Long unidadeId,
    String portaria,
    LocalDate dataInicioLotacao,
    LocalDate dataFimLotacao,
    LocalDate dataInicioRemocao,
    LocalDate dataFimRemocao,
    Pageable pageable
) {
}
