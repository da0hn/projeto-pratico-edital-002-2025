package br.com.ghonda.core.service;

import br.com.ghonda.core.domain.FotoPessoa;
import br.com.ghonda.core.dto.FotoPessoaDetailPayload;
import br.com.ghonda.core.exceptions.ResourceNotFoundException;
import br.com.ghonda.core.repository.FotoPessoaRepository;
import br.com.ghonda.core.repository.PessoaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FotoPessoaService {

    private final FotoPessoaRepository fotoPessoaRepository;

    private final FileSystemService fileSystemService;

    private final PessoaRepository pessoaRepository;

    @Transactional
    public void upload(final Long pessoaId, final MultipartFile file) {
        log.debug("m=upload(pessoaId={}, file={})", pessoaId, file);

        final var pessoa = this.pessoaRepository.findById(pessoaId)
            .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrado"));

        if (file.isEmpty()) {
            log.warn("Arquivo vazio. Nenhum upload realizado.");
            throw new IllegalStateException("O arquivo informado está vazio");
        }

        final var builder = FotoPessoa.builder()
            .id(null)
            .pessoa(pessoa)
            .data(LocalDateTime.now());

        final var fileDetailPayload = this.fileSystemService.uploadObject(file);

        final var fotoPessoa = builder.bucket(fileDetailPayload.bucketName())
            .hash(fileDetailPayload.objectName())
            .build();

        this.fotoPessoaRepository.save(fotoPessoa);

        log.info("Foto do pessoa {} registrada com sucesso", pessoa);
    }

    public List<FotoPessoaDetailPayload> findAll(final Long pessoaId) {
        return this.fotoPessoaRepository.findAllByPessoaId(pessoaId).stream()
            .map(foto -> FotoPessoaDetailPayload.of(foto, this.fileSystemService.getObject(foto.getHash())))
            .toList();
    }

}
