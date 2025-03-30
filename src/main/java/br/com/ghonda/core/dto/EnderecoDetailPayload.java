package br.com.ghonda.core.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDetailPayload(
    @NotBlank(message = "Tipo de logradouro é obrigatório")
    String tipoLogradouro,
    @NotBlank(message = "Logradouro é obrigatório")
    String logradouro,
    @NotNull(message = "Número é obrigatório")
    Long numero,
    @NotBlank(message = "Bairro é obrigatório")
    String bairro,
    @Valid
    CidadeDetailPayload cidade
) {

}
