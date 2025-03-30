package br.com.ghonda.core.dto;

import java.util.Set;

public record UpdateUnidadePayload(
    Long id,
    String nome,
    String sigla,
    Set<EnderecoDetailPayload> enderecos
) {

}
