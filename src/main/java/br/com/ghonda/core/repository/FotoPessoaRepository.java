package br.com.ghonda.core.repository;

import br.com.ghonda.core.domain.FotoPessoa;
import br.com.ghonda.core.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FotoPessoaRepository extends JpaRepository<FotoPessoa, Long> {

    @Query("SELECT f FROM FotoPessoa f WHERE f.pessoa.id = :pessoaId")
    List<FotoPessoa> findAllByPessoaId(Long pessoaId);

}
