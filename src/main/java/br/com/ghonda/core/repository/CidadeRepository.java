package br.com.ghonda.core.repository;

import br.com.ghonda.core.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface CidadeRepository extends JpaRepository<Cidade, Long>, QueryByExampleExecutor<Cidade> {
}
