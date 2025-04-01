package br.com.ghonda.core.repository;

import br.com.ghonda.core.domain.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>, QueryByExampleExecutor<Endereco> {

    @Query(
        value = """
                select e.*
                from endereco e
                inner join unidade_endereco ue on e.end_id = ue.end_id
                inner join unidade u on ue.unid_id = u.unid_id
                inner join lotacao l on u.unid_id = l.unid_id
                inner join public.pessoa p on l.pes_id = p.pes_id
                where lower(p.pes_nome) like lower(concat('%', :nomeServidorEfetivo, '%'))
                """,
        countQuery = """
                     select count(*)
                     from endereco e
                     inner join unidade_endereco ue on e.end_id = ue.end_id
                     inner join unidade u on ue.unid_id = u.unid_id
                     inner join lotacao l on u.unid_id = l.unid_id
                     inner join public.pessoa p on l.pes_id = p.pes_id
                     where lower(p.pes_nome) like lower(concat('%', :nomeServidorEfetivo, '%'))
                     """,
        nativeQuery = true
    )
    Page<Endereco> findEnderecoFuncionalUnidade(String nomeServidorEfetivo, Pageable pageable);

}
