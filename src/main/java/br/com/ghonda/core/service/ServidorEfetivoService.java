package br.com.ghonda.core.service;

import br.com.ghonda.core.domain.ServidorEfetivo;
import br.com.ghonda.core.dto.NewServidorEfetivoPayload;
import br.com.ghonda.core.dto.SearchServidorEfetivoPayload;
import br.com.ghonda.core.dto.ServidorSimpleDetailPayload;
import br.com.ghonda.core.dto.UpdateServidorEfetivoPayload;
import br.com.ghonda.core.exceptions.ResourceNotFoundException;
import br.com.ghonda.core.repository.ServidorEfetivoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class ServidorEfetivoService {

    private final ServidorEfetivoRepository servidorEfetivoRepository;

    private final EnderecoService enderecoService;

    @Transactional
    public ServidorSimpleDetailPayload registrar(final NewServidorEfetivoPayload payload) {
        log.debug("m=registrar(payload={})", payload);

        final var newServidorEfetivo = ServidorEfetivo.of(payload);

        payload.pessoa().enderecos().stream()
            .map(this.enderecoService::findOrCreate)
            .forEach(newServidorEfetivo::addEndereco);

        this.servidorEfetivoRepository.save(newServidorEfetivo);

        log.info("Servidor efetivo {} registrado com sucesso", newServidorEfetivo);

        return ServidorSimpleDetailPayload.of(newServidorEfetivo);
    }

    @Transactional(readOnly = true)
    public ServidorSimpleDetailPayload findById(final Long id) {
        log.debug("m=findById(id={})", id);
        return this.servidorEfetivoRepository.findById(id)
            .map(ServidorSimpleDetailPayload::of)
            .orElseThrow(() -> new ResourceNotFoundException("Servidor efetivo não encontrado"));
    }

    @Transactional
    public ServidorSimpleDetailPayload update(final UpdateServidorEfetivoPayload payload) {
        log.debug("m=update(payload={})", payload);
        final var servidorEfetivo = this.servidorEfetivoRepository.findById(payload.id())
            .orElseThrow(() -> new ResourceNotFoundException("Servidor efetivo não encontrado"));

        servidorEfetivo.setMatricula(payload.matricula());
        servidorEfetivo.setNome(payload.pessoa().nome());
        servidorEfetivo.setNomeMae(payload.pessoa().nomeMae());
        servidorEfetivo.setNomePai(payload.pessoa().nomePai());
        servidorEfetivo.setSexo(payload.pessoa().sexo());
        servidorEfetivo.setDataNascimento(payload.pessoa().dataNascimento());

        servidorEfetivo.getEnderecos().clear();

        payload.pessoa().enderecos().stream()
            .map(this.enderecoService::findOrCreate)
            .forEach(servidorEfetivo::addEndereco);

        this.servidorEfetivoRepository.save(servidorEfetivo);

        log.info("Servidor efetivo {} atualizado com sucesso", servidorEfetivo);

        return ServidorSimpleDetailPayload.of(servidorEfetivo);
    }

    @Transactional(readOnly = true)
    public Page<ServidorSimpleDetailPayload> findAll(final SearchServidorEfetivoPayload payload) {
        return this.servidorEfetivoRepository.findAllBy(
            payload.nome(),
            payload.matricula(),
            payload.nomeCidade(),
            payload.uf(),
            payload.pageable()
        ).map(ServidorSimpleDetailPayload::of);
    }

}
