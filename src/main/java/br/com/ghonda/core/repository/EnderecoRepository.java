package br.com.ghonda.core.repository;

import br.com.ghonda.core.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>, QueryByExampleExecutor<Endereco> {
}
