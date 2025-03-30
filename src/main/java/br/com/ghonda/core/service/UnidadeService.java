package br.com.ghonda.core.service;

import br.com.ghonda.core.domain.Unidade;
import br.com.ghonda.core.dto.NewUnidadePayload;
import br.com.ghonda.core.dto.UnidadeDetailPayload;
import br.com.ghonda.core.dto.UpdateUnidadePayload;
import br.com.ghonda.core.repository.UnidadeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    private final EnderecoService enderecoService;

    @Transactional
    public UnidadeDetailPayload registrar(final NewUnidadePayload payload) {
        log.debug("m=registrar(payload={})", payload);

        final var newUnidade = Unidade.of(payload);

        payload.enderecos().stream()
            .map(this.enderecoService::findOrCreate)
            .forEach(newUnidade::addEndereco);

        this.unidadeRepository.save(newUnidade);

        log.info("Unidade registrada com sucesso: {}", newUnidade);

        return UnidadeDetailPayload.of(newUnidade);
    }

    @Transactional(readOnly = true)
    public UnidadeDetailPayload findById(final Long id) {
        log.debug("m=findById(id={})", id);

        return this.unidadeRepository.findById(id)
            .map(UnidadeDetailPayload::of)
            .orElseThrow(() -> new IllegalArgumentException("Unidade não encontrada"));
    }

    @Transactional
    public UnidadeDetailPayload update(final UpdateUnidadePayload payload) {
        log.debug("m=update(payload={})", payload);

        final var unidade = this.unidadeRepository.findById(payload.id())
            .orElseThrow(() -> new IllegalArgumentException("Unidade não encontrada"));

        unidade.setNome(payload.nome());
        unidade.setSigla(payload.sigla());

        unidade.getEnderecos().clear();

        payload.enderecos().stream()
            .map(this.enderecoService::findOrCreate)
            .forEach(unidade::addEndereco);

        this.unidadeRepository.save(unidade);

        log.info("Unidade atualizada com sucesso: {}", unidade);

        return UnidadeDetailPayload.of(unidade);
    }

}
