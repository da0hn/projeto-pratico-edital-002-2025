package br.com.ghonda.core.dto;

import br.com.ghonda.core.domain.UF;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
public record SearchServidorEfetivoPayload(
    String nome,
    String matricula,
    String nomeCidade,
    UF uf,
    Pageable pageable
) {
}
