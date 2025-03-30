package br.com.ghonda.core.dto;

import br.com.ghonda.core.domain.Unidade;

import java.util.List;

public record UnidadeDetailPayload(
    Long id,
    String nome,
    String sigla,
    List<EnderecoDetailPayload> enderecos
) {

    public static UnidadeDetailPayload of(final Unidade unidade) {
        return new UnidadeDetailPayload(
            unidade.getId(),
            unidade.getNome(),
            unidade.getSigla(),
            unidade.getEnderecos().stream()
                .map(EnderecoDetailPayload::of)
                .toList()
        );
    }

}
