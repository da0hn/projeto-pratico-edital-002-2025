package br.com.ghonda.core.repository;

import br.com.ghonda.core.domain.Lotacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotacaoRepository extends JpaRepository<Lotacao, Long> {
}
