package br.com.ghonda.core.dto;

import br.com.ghonda.core.domain.Endereco;
import br.com.ghonda.core.domain.Lotacao;
import br.com.ghonda.core.domain.Pessoa;
import br.com.ghonda.core.domain.Unidade;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Jacksonized
public record EnderecoFuncionalDetailPayload(
    String tipoLogradouro,
    String logradouro,
    Long numero,
    String bairro,
    CidadeDetailPayload cidade,
    List<UnidadePayload> unidades
) {

    public static EnderecoFuncionalDetailPayload of(final Endereco endereco) {
        return new EnderecoFuncionalDetailPayload(
            endereco.getTipoLogradouro(),
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getBairro(),
            CidadeDetailPayload.of(endereco.getCidade()),
            endereco.getUnidades()
                .stream()
                .map(UnidadePayload::of)
                .toList()
        );
    }

    @Builder
    public record UnidadePayload(Long id, String nome, String sigla, Set<ServidorPayload> servidores) {

        public static UnidadePayload of(final Unidade unidade) {
            return UnidadePayload.builder()
                .id(unidade.getId())
                .nome(unidade.getNome())
                .sigla(unidade.getSigla())
                .servidores(unidade.getLotacoes()
                                .stream()
                                .map(Lotacao::getPessoa)
                                .map(ServidorPayload::of)
                                .collect(Collectors.toSet())
                )
                .build();
        }

    }

    public record ServidorPayload(Long id, String nome) {

        public static ServidorPayload of(final Pessoa pessoa) {
            return new ServidorPayload(
                pessoa.getId(),
                pessoa.getNome()
            );
        }

    }

}
