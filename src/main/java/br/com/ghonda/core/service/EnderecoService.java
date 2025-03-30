package br.com.ghonda.core.service;

import br.com.ghonda.core.domain.Cidade;
import br.com.ghonda.core.domain.Endereco;
import br.com.ghonda.core.dto.EnderecoDetailPayload;
import br.com.ghonda.core.repository.CidadeRepository;
import br.com.ghonda.core.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class EnderecoService {

    private final CidadeRepository cidadeRepository;

    private final EnderecoRepository enderecoRepository;

    @Transactional
    public Endereco findOrCreate(final EnderecoDetailPayload payload) {
        if (log.isDebugEnabled()) {
            log.debug("m=findOrCreate(payload={})", payload);
        }

        final var maybeCidade = this.cidadeRepository.findOne(
            Example.of(
                Cidade.of(payload.cidade()),
                ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withIgnorePaths("id")
                    .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("sigla", ExampleMatcher.GenericPropertyMatchers.exact())
            )
        );

        if (maybeCidade.isEmpty()) {
            log.info("Cidade informada não encontrada, cadastrando cidade {} na base de dados", payload.cidade());

            return this.handleNewCidade(payload);
        }

        log.info("Cidade informada já cadastrada na base de dados, verificando existência do endereço {}", payload);

        return this.handleNewEndereco(payload, maybeCidade.get());
    }

    private Endereco handleNewEndereco(final EnderecoDetailPayload payload, final Cidade cidade) {
        log.debug("m=handleNewEndereco(payload={}, cidade={})", payload, cidade);

        final var newEndereco = Endereco.builder()
            .tipoLogradouro(payload.tipoLogradouro())
            .logradouro(payload.logradouro())
            .numero(payload.numero())
            .bairro(payload.bairro())
            .cidade(cidade)
            .build();

        final var maybeEndereco = this.enderecoRepository.findOne(
            Example.of(
                newEndereco,
                ExampleMatcher.matchingAll()
                    .withIgnoreNullValues()
                    .withIgnorePaths("id")
                    .withMatcher("tipoLogradouro", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("logradouro", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("numero", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("bairro", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("cidade", ExampleMatcher.GenericPropertyMatchers.exact())
            )
        );

        if (maybeEndereco.isPresent()) {
            log.info("Endereço já cadastrado na base de dados, retornando o endereço existente");
            return maybeEndereco.get();
        }

        log.info("Endereço não encontrado, cadastrando novo endereço na base de dados");
        this.enderecoRepository.save(newEndereco);

        return newEndereco;
    }

    private Endereco handleNewCidade(final EnderecoDetailPayload payload) {
        log.debug("m=handleNewCidade(payload={})", payload);

        final var newCidade = Cidade.of(payload.cidade());

        this.cidadeRepository.save(newCidade);

        log.info("Cidade cadastrada com sucesso na base de dados, cadastrando novo endereço. Cidade: {}", newCidade);

        final var newEndereco = Endereco.builder()
            .tipoLogradouro(payload.tipoLogradouro())
            .logradouro(payload.logradouro())
            .numero(payload.numero())
            .bairro(payload.bairro())
            .cidade(newCidade)
            .build();

        this.enderecoRepository.save(newEndereco);

        log.info("Endereço cadastrado com sucesso na base de dados. Endereço: {}", newEndereco);

        return newEndereco;
    }

}
