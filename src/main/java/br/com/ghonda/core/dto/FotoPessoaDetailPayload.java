package br.com.ghonda.core.dto;

import br.com.ghonda.core.domain.FotoPessoa;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FotoPessoaDetailPayload(
    String url,
    String hash,
    LocalDateTime data,
    String bucket
) {

    public static FotoPessoaDetailPayload of(final FotoPessoa fotoPessoa, final String url) {
        return FotoPessoaDetailPayload.builder()
            .url(url)
            .hash(fotoPessoa.getHash())
            .data(fotoPessoa.getData())
            .bucket(fotoPessoa.getBucket())
            .build();
    }

}
