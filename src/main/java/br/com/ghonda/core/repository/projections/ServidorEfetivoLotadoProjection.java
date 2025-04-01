package br.com.ghonda.core.repository.projections;

import java.time.LocalDate;

public interface ServidorEfetivoLotadoProjection {

    Long getId();

    String getNome();

    LocalDate getDataNascimento();

    Long getUnidadeId();

    String getUnidadeNome();

    String getUnidadeSigla();

}
