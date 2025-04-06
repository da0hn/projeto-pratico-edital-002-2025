package br.com.ghonda.core.service;

import br.com.ghonda.core.domain.ServidorTemporario;
import br.com.ghonda.core.dto.NewServidorTemporarioPayload;
import br.com.ghonda.core.dto.SearchServidorTemporarioPayload;
import br.com.ghonda.core.dto.ServidorSimpleDetailPayload;
import br.com.ghonda.core.dto.UpdateServidorTemporarioPayload;
import br.com.ghonda.core.exceptions.ResourceNotFoundException;
import br.com.ghonda.core.repository.ServidorTemporarioRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class ServidorTemporarioService {

    private final ServidorTemporarioRepository servidorTemporarioRepository;

    private final EnderecoService enderecoService;

    @Transactional
    public ServidorSimpleDetailPayload registrar(final NewServidorTemporarioPayload payload) {
        log.debug("m=registrarServidorTemporario(payload={})", payload);

        final var newServidorTemporario = ServidorTemporario.of(payload);

        payload.pessoa().enderecos().stream()
            .map(this.enderecoService::findOrCreate)
            .forEach(newServidorTemporario::addEndereco);

        this.servidorTemporarioRepository.save(newServidorTemporario);

        log.info("Servidor temporário registrado com sucesso: {}", newServidorTemporario);

        return ServidorSimpleDetailPayload.of(newServidorTemporario);
    }

    @Transactional(readOnly = true)
    public ServidorSimpleDetailPayload findById(final Long id) {
        log.debug("m=findById(id={})", id);
        return this.servidorTemporarioRepository.findById(id)
            .map(ServidorSimpleDetailPayload::of)
            .orElseThrow(() -> new ResourceNotFoundException("Servidor temporário não encontrado"));
    }

    @Transactional
    public ServidorSimpleDetailPayload update(@Valid final UpdateServidorTemporarioPayload payload) {
        log.debug("m=updateServidorTemporario(payload={})", payload);

        final var servidorTemporario = this.servidorTemporarioRepository.findById(payload.id())
            .orElseThrow(() -> new ResourceNotFoundException("Servidor temporário não encontrado"));

        servidorTemporario.setNome(payload.pessoa().nome());
        servidorTemporario.setNomeMae(payload.pessoa().nomeMae());
        servidorTemporario.setNomePai(payload.pessoa().nomePai());
        servidorTemporario.setSexo(payload.pessoa().sexo());
        servidorTemporario.setDataNascimento(payload.pessoa().dataNascimento());
        servidorTemporario.setDataAdmissao(payload.dataAdmissao());
        servidorTemporario.setDataDemissao(payload.dataDemissao());

        servidorTemporario.getEnderecos().clear();

        payload.pessoa().enderecos().stream()
            .map(this.enderecoService::findOrCreate)
            .forEach(servidorTemporario::addEndereco);

        this.servidorTemporarioRepository.save(servidorTemporario);

        log.info("Servidor temporário atualizado com sucesso: {}", servidorTemporario);

        return ServidorSimpleDetailPayload.of(servidorTemporario);
    }

    public Page<ServidorSimpleDetailPayload> findAll(final SearchServidorTemporarioPayload payload) {
        return this.servidorTemporarioRepository.findAll(
            payload.nome(),
            payload.nomeCidade(),
            payload.uf(),
            payload.pageable()
        ).map(ServidorSimpleDetailPayload::of);
    }

}
