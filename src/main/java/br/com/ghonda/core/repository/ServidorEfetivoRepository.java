package br.com.ghonda.core.repository;

import br.com.ghonda.core.domain.ServidorEfetivo;
import br.com.ghonda.core.domain.UF;
import br.com.ghonda.core.repository.projections.ServidorEfetivoLotadoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Long> {

    @Query(value = """
                   select distinct efetivo
                   from ServidorEfetivo efetivo
                      inner join efetivo.enderecos enderecos
                      inner join enderecos.cidade cidade
                   where (:nome is null or efetivo.nome like lower(concat('%', cast(:nome as string), '%'))) and
                      (:matricula is null or efetivo.matricula like lower(concat('%', cast(:matricula as string), '%'))) and
                      (:nomeCidade is null or cidade.nome like lower(concat('%', cast(:nomeCidade as string), '%'))) and
                      (:uf is null or cidade.uf = :uf)
                   """,
           countQuery = """
                        select count(distinct efetivo)
                        from ServidorEfetivo efetivo
                           inner join efetivo.enderecos enderecos
                           inner join enderecos.cidade cidade
                        where (:nome is null or efetivo.nome like lower(concat('%', cast(:nome as string), '%'))) and
                           (:matricula is null or efetivo.matricula like lower(concat('%', cast(:matricula as string), '%'))) and
                           (:nomeCidade is null or cidade.nome like lower(concat('%', cast(:nomeCidade as string), '%'))) and
                           (:uf is null or cidade.uf = :uf)
                        """)
    Page<ServidorEfetivo> findAllBy(
        String nome,
        String matricula,
        String nomeCidade,
        UF uf,
        Pageable pageable
    );

    @Query(
        value = """
                  select
                    efetivo.pes_id as id,
                    pessoa.pes_nome as nome,
                    pessoa.pes_data_nascimento as dataNascimento,
                    unidade.unid_id as unidadeId,
                    unidade.unid_nome as unidadeNome,
                    unidade.unid_sigla as unidadeSigla
                  from servidor_efetivo efetivo
                  inner join pessoa pessoa on efetivo.pes_id = pessoa.pes_id
                  inner join lotacao lotacao on efetivo.pes_id = lotacao.pes_id
                  inner join unidade unidade on lotacao.unid_id = unidade.unid_id
                  where lotacao.unid_id = :unidadeId
                """,
        countQuery = """
                     select count(*)
                     from servidor_efetivo efetivo
                     inner join pessoa pessoa on efetivo.pes_id = pessoa.pes_id
                     inner join lotacao lotacao on efetivo.pes_id = lotacao.pes_id
                     inner join unidade unidade on lotacao.unid_id = unidade.unid_id
                     where lotacao.unid_id = :unidadeId
                     """,
        nativeQuery = true
    )
    Page<ServidorEfetivoLotadoProjection> findAllLotados(Long unidadeId, Pageable pageable);

}
