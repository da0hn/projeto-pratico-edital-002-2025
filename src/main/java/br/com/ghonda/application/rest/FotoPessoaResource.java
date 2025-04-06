package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiCollectionResponse;
import br.com.ghonda.core.dto.FotoPessoaDetailPayload;
import br.com.ghonda.core.service.FotoPessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pessoa", description = "Endpoints para gestão de pessoas")
public class FotoPessoaResource {

    private final FotoPessoaService fotoPessoaService;

    @PostMapping("/{pessoaId}/imagem")
    @Operation(
        summary = "Upload de imagem para pessoa",
        description = "Endpoint para fazer o upload de uma imagem para uma pessoa",
        tags = "Pessoa"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Imagem enviada com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Dados inválidos fornecidos"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<Void> upload(
        @PathVariable("pessoaId") final Long pessoaId,
        @RequestParam("file") final MultipartFile file
    ) {
        log.info("Upload da imagem para pessoa com id: {}", pessoaId);

        this.fotoPessoaService.upload(pessoaId, file);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{pessoaId}/imagem")
    @Operation(
        summary = "Buscar imagens para pessoa",
        description = "Endpoint para buscar todas as imagens de uma pessoa",
        tags = "Pessoa"
    )
    @ApiResponses(
        value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Imagens encontradas",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = FotoPessoaDetailPayload.class)
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Pessoa não encontrada"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Erro interno no servidor"
            )
        }
    )
    public ResponseEntity<ApiCollectionResponse<FotoPessoaDetailPayload>> findAll(@PathVariable("pessoaId") final Long pessoaId) {
        log.info("Buscar imagens para pessoa com id: {}", pessoaId);

        final var response = this.fotoPessoaService.findAll(pessoaId);

        return ResponseEntity.ok(ApiCollectionResponse.of(response));
    }

}
