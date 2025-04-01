package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiCollectionResponse;
import br.com.ghonda.core.dto.FotoPessoaDetailPayload;
import br.com.ghonda.core.service.FotoPessoaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/pessoas")
public class FotoPessoaResource {

    private final FotoPessoaService fotoPessoaService;

    @PostMapping("/{pessoaId}/imagem")
    public ResponseEntity<Void> upload(
        @PathVariable("pessoaId") final Long pessoaId,
        @RequestParam("file") final MultipartFile file
    ) {

        this.fotoPessoaService.upload(pessoaId, file);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{pessoaId}/imagem")
    public ResponseEntity<ApiCollectionResponse<FotoPessoaDetailPayload>> findAll(@PathVariable("pessoaId") final Long pessoaId) {

        final var response = this.fotoPessoaService.findAll(pessoaId);

        return ResponseEntity.ok(ApiCollectionResponse.of(response));
    }

}
