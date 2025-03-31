package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiResponse;
import br.com.ghonda.core.dto.LotacaoDetailPayload;
import br.com.ghonda.core.dto.NewLotacaoPayload;
import br.com.ghonda.core.service.LotacaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/lotacoes")
public class LotacaoResource {

    private final LotacaoService lotacaoService;

    @PostMapping
    public ResponseEntity<ApiResponse<LotacaoDetailPayload>> registrarLotacao(@Valid @RequestBody final NewLotacaoPayload payload) {
        log.debug("m=registrarLotacao(payload={})", payload);

        final var response = this.lotacaoService.registrar(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.of(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LotacaoDetailPayload>> atualizarLotacao(@Valid @RequestBody final LotacaoDetailPayload payload) {
        log.debug("m=atualizarLotacao(payload={})", payload);

        final var response = this.lotacaoService.update(payload);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.of(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LotacaoDetailPayload>> findById(@PathVariable final Long id) {
        log.debug("m=findById(id={})", id);

        final var response = this.lotacaoService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.of(response));
    }

}
