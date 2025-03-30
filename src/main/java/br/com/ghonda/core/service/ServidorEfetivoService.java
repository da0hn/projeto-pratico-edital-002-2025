package br.com.ghonda.core.service;

import br.com.ghonda.core.domain.ServidorEfetivo;
import br.com.ghonda.core.dto.NewServidorEfetivoPayload;
import br.com.ghonda.core.dto.ServidorSimpleDetailPayload;
import br.com.ghonda.core.repository.ServidorEfetivoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}
