package br.com.ghonda.core.service;

import br.com.ghonda.core.domain.Lotacao;
import br.com.ghonda.core.dto.LotacaoDetailPayload;
import br.com.ghonda.core.dto.NewLotacaoPayload;
import br.com.ghonda.core.dto.SearchLotacaoPayload;
import br.com.ghonda.core.dto.UpdateLotacaoPayload;
import br.com.ghonda.core.exceptions.ResourceNotFoundException;
import br.com.ghonda.core.repository.LotacaoRepository;
import br.com.ghonda.core.repository.PessoaRepository;
import br.com.ghonda.core.repository.UnidadeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class LotacaoService {

    private final LotacaoRepository lotacaoRepository;

    private final PessoaRepository pessoaRepository;

    private final UnidadeRepository unidadeRepository;

    @Transactional
    public LotacaoDetailPayload registrar(final NewLotacaoPayload payload) {
        log.debug("m=registrar(payload={})", payload);

        final var unidade = this.unidadeRepository.findById(payload.unidadeId())
            .orElseThrow(() -> new ResourceNotFoundException("Unidade não encontrada"));

        final var pessoa = this.pessoaRepository.findById(payload.servidorId())
            .orElseThrow(() -> new ResourceNotFoundException("Servidor não encontrado"));

        final var newLotacao = Lotacao.of(payload);
        newLotacao.setUnidade(unidade);
        newLotacao.setPessoa(pessoa);

        this.lotacaoRepository.save(newLotacao);

        return LotacaoDetailPayload.of(newLotacao);
    }

    @Transactional(readOnly = true)
    public LotacaoDetailPayload findById(final Long id) {
        log.debug("m=findById(id={})", id);

        return this.lotacaoRepository.findById(id)
            .map(LotacaoDetailPayload::of)
            .orElseThrow(() -> new ResourceNotFoundException("Lotação não encontrada"));
    }

    @Transactional
    public LotacaoDetailPayload update(final UpdateLotacaoPayload payload) {
        log.debug("m=update(payload={})", payload);

        final var lotacao = this.lotacaoRepository.findById(payload.id())
            .orElseThrow(() -> new ResourceNotFoundException("Lotação não encontrada"));

        lotacao.setDataLotacao(payload.dataLotacao());
        lotacao.setDataRemocao(payload.dataRemocao());
        lotacao.setPortaria(payload.portaria());

        this.lotacaoRepository.save(lotacao);

        return LotacaoDetailPayload.of(lotacao);
    }

    public Page<LotacaoDetailPayload> findAll(SearchLotacaoPayload payload) {
        return this.lotacaoRepository.findAllBy(
            payload.id(),
            payload.servidorId(),
            payload.unidadeId(),
            payload.portaria(),
            payload.dataInicioLotacao(),
            payload.dataFimLotacao(),
            payload.dataInicioRemocao(),
            payload.dataFimRemocao(),
            payload.pageable()
        ).map(LotacaoDetailPayload::of);
    }

}
