package br.com.ghonda.core.dto;

import br.com.ghonda.core.domain.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
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

    public static EnderecoDetailPayload of(final Endereco endereco) {
        return new EnderecoDetailPayload(
            endereco.getTipoLogradouro(),
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getBairro(),
            CidadeDetailPayload.of(endereco.getCidade())
        );
    }

}
