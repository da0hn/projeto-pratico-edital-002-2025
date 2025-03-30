package br.com.ghonda.core.dto;

import java.util.Set;

public record NewUnidadePayload(
    String nome,
    String sigla,
    Set<EnderecoDetailPayload> enderecos
) {



}
